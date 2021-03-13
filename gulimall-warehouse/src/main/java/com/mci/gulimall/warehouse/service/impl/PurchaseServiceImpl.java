package com.mci.gulimall.warehouse.service.impl;

import com.mci.common.constant.WarehouseConstant;
import com.mci.gulimall.warehouse.service.WareSkuService;
import com.mci.gulimall.warehouse.vo.MergeVo;
import com.mci.gulimall.warehouse.entity.PurchaseDetailEntity;
import com.mci.gulimall.warehouse.service.PurchaseDetailService;
import com.mci.gulimall.warehouse.vo.PurchaseDoneVo;
import com.mci.gulimall.warehouse.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.Query;

import com.mci.gulimall.warehouse.dao.PurchaseDao;
import com.mci.gulimall.warehouse.entity.PurchaseEntity;
import com.mci.gulimall.warehouse.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseDetailService detailService;

    @Autowired
    WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceivePurchase(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>().eq("status", 0).eq("status", 1)
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();

            purchaseEntity.setStatus(WarehouseConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());

            this.save(purchaseEntity);

            purchaseId = purchaseEntity.getId();
        }

        List<Long> items = mergeVo.getItems();
        Long finalPurchaseId = purchaseId;

        List<PurchaseDetailEntity> collect = items.stream().map(item -> {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();

            detailEntity.setId(item);
            detailEntity.setPurchaseId(finalPurchaseId);
            detailEntity.setStatus(WarehouseConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());

            return detailEntity;
        }).collect(Collectors.toList());

        detailService.updateBatchById(collect);

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseId);
        purchaseEntity.setUpdateTime(new Date());

        this.updateById(purchaseEntity);
    }

    @Override
    public void received(List<Long> ids) {
        // 1. check current purchase list is newly created or assigned
        List<PurchaseEntity> collect = ids.stream().map(id -> {
            PurchaseEntity byId = this.getById(id);
            return byId;
        }).filter(item -> {
            if (item.getStatus() == WarehouseConstant.PurchaseStatusEnum.CREATED.getCode()
                    || item.getStatus() == WarehouseConstant.PurchaseStatusEnum.ASSIGNED.getCode()) {
                return true;
            }
            return false;
        }).map(item -> {
            item.setStatus(WarehouseConstant.PurchaseStatusEnum.RECEIVE.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());

        // 2. modify the purchase list status
        this.updateBatchById(collect);

        // 3. modify the purchase items status
        collect.forEach(item -> {
            List<PurchaseDetailEntity> entities = detailService.listDetailByPurchaseId(item.getId());

            List<PurchaseDetailEntity> detailEntities = entities.stream().map(entity -> {
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setId(entity.getId());
                purchaseDetailEntity.setStatus(WarehouseConstant.PurchaseDetailStatusEnum.BUYING.getCode());

                return purchaseDetailEntity;
            }).collect(Collectors.toList());

            detailService.updateBatchById(detailEntities);
        });
    }

    @Transactional
    @Override
    public void done(PurchaseDoneVo doneVo) {
        Long id = doneVo.getId();

        // 1. modify purchase items status
        Boolean purchaseSuccessful = true;
        List<PurchaseItemDoneVo> items = doneVo.getItems();

        List<PurchaseDetailEntity> updates = new ArrayList<>();

        for (PurchaseItemDoneVo item : items) {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            if (item.getStatus() == WarehouseConstant.PurchaseDetailStatusEnum.FAIL.getCode()) {
                purchaseSuccessful = false;
                detailEntity.setStatus(item.getStatus());
            } else {
                detailEntity.setStatus(WarehouseConstant.PurchaseDetailStatusEnum.FINISH.getCode());

                // modify items stocks
                PurchaseDetailEntity purchaseDetailEntity = detailService.getById(item.getItemId());
                wareSkuService.addStock(purchaseDetailEntity.getSkuId(), purchaseDetailEntity.getWareId(),
                        purchaseDetailEntity.getSkuNum());
            }

            detailEntity.setId(item.getItemId());
            updates.add(detailEntity);
        }

        detailService.updateBatchById(updates);

        // 2. modify purchase list status
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(id);
        purchaseEntity.setStatus(purchaseSuccessful ? WarehouseConstant.PurchaseStatusEnum.FINISH.getCode()
                : WarehouseConstant.PurchaseStatusEnum.ERROR.getCode());
        purchaseEntity.setUpdateTime(new Date());

        this.updateById(purchaseEntity);
    }

}