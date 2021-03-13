package com.mci.gulimall.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.warehouse.vo.MergeVo;
import com.mci.gulimall.warehouse.entity.PurchaseEntity;
import com.mci.gulimall.warehouse.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-03-06 22:47:52
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids);

    void done(PurchaseDoneVo doneVo);
}

