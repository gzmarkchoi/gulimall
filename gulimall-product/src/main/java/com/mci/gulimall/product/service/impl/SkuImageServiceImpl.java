package com.mci.gulimall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.Query;

import com.mci.gulimall.product.dao.SkuImageDao;
import com.mci.gulimall.product.entity.SkuImageEntity;
import com.mci.gulimall.product.service.SkuImageService;


@Service("skuImageService")
public class SkuImageServiceImpl extends ServiceImpl<SkuImageDao, SkuImageEntity> implements SkuImageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuImageEntity> page = this.page(
                new Query<SkuImageEntity>().getPage(params),
                new QueryWrapper<SkuImageEntity>()
        );

        return new PageUtils(page);
    }

}