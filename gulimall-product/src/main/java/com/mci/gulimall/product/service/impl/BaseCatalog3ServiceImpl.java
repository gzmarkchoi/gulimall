package com.mci.gulimall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.Query;

import com.mci.gulimall.product.dao.BaseCatalog3Dao;
import com.mci.gulimall.product.entity.BaseCatalog3Entity;
import com.mci.gulimall.product.service.BaseCatalog3Service;


@Service("baseCatalog3Service")
public class BaseCatalog3ServiceImpl extends ServiceImpl<BaseCatalog3Dao, BaseCatalog3Entity> implements BaseCatalog3Service {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseCatalog3Entity> page = this.page(
                new Query<BaseCatalog3Entity>().getPage(params),
                new QueryWrapper<BaseCatalog3Entity>()
        );

        return new PageUtils(page);
    }

}