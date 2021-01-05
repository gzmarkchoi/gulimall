package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.ProductFullReductionEntity;

import java.util.Map;

/**
 * 产品满减表(只针对同商品)
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
public interface ProductFullReductionService extends IService<ProductFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

