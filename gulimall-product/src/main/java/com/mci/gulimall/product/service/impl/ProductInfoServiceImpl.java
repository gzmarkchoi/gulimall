package com.mci.gulimall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.Query;

import com.mci.gulimall.product.dao.ProductInfoDao;
import com.mci.gulimall.product.entity.ProductInfoEntity;
import com.mci.gulimall.product.service.ProductInfoService;


@Service("productInfoService")
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoDao, ProductInfoEntity> implements ProductInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductInfoEntity> page = this.page(
                new Query<ProductInfoEntity>().getPage(params),
                new QueryWrapper<ProductInfoEntity>()
        );

        return new PageUtils(page);
    }

}