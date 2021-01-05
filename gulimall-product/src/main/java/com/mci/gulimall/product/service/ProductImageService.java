package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.ProductImageEntity;

import java.util.Map;

/**
 * 商品图片表
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
public interface ProductImageService extends IService<ProductImageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

