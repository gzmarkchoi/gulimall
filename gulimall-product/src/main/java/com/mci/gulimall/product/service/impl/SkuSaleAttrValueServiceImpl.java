package com.mci.gulimall.product.service.impl;

import com.mci.gulimall.product.vo.SkuItemSalesAttrsVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.Query;

import com.mci.gulimall.product.dao.SkuSaleAttrValueDao;
import com.mci.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.mci.gulimall.product.service.SkuSaleAttrValueService;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuItemSalesAttrsVo> getSaleAttrsBySpuId(Long spuId) {
        SkuSaleAttrValueDao dao = this.baseMapper;
        List<SkuItemSalesAttrsVo> saleAttrsVos = dao.getSaleAttrsBySpuId(spuId);

        return saleAttrsVos;
    }

}