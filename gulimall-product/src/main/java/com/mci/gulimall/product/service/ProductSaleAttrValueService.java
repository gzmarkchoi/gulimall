package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.ProductSaleAttrValueEntity;

import java.util.Map;

/**
 * spu销售属性值
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
public interface ProductSaleAttrValueService extends IService<ProductSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

