package com.mci.gulimall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.Query;

import com.mci.gulimall.product.dao.SkuAttrValueDao;
import com.mci.gulimall.product.entity.SkuAttrValueEntity;
import com.mci.gulimall.product.service.SkuAttrValueService;


@Service("skuAttrValueService")
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueDao, SkuAttrValueEntity> implements SkuAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuAttrValueEntity> page = this.page(
                new Query<SkuAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuAttrValueEntity>()
        );

        return new PageUtils(page);
    }

}