package com.mci.gulimall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.Query;

import com.mci.gulimall.product.dao.BaseSaleAttrDao;
import com.mci.gulimall.product.entity.BaseSaleAttrEntity;
import com.mci.gulimall.product.service.BaseSaleAttrService;


@Service("baseSaleAttrService")
public class BaseSaleAttrServiceImpl extends ServiceImpl<BaseSaleAttrDao, BaseSaleAttrEntity> implements BaseSaleAttrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseSaleAttrEntity> page = this.page(
                new Query<BaseSaleAttrEntity>().getPage(params),
                new QueryWrapper<BaseSaleAttrEntity>()
        );

        return new PageUtils(page);
    }

}