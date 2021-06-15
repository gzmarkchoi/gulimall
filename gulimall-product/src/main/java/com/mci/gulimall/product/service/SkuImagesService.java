package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.SkuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * sku图片
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 15:50:34
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuImagesEntity> getImagesBySkuId(Long skuId);
}

