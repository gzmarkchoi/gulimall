package com.mci.gulimall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.Query;

import com.mci.gulimall.product.dao.ProductVertifyRecordDao;
import com.mci.gulimall.product.entity.ProductVertifyRecordEntity;
import com.mci.gulimall.product.service.ProductVertifyRecordService;


@Service("productVertifyRecordService")
public class ProductVertifyRecordServiceImpl extends ServiceImpl<ProductVertifyRecordDao, ProductVertifyRecordEntity> implements ProductVertifyRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductVertifyRecordEntity> page = this.page(
                new Query<ProductVertifyRecordEntity>().getPage(params),
                new QueryWrapper<ProductVertifyRecordEntity>()
        );

        return new PageUtils(page);
    }

}