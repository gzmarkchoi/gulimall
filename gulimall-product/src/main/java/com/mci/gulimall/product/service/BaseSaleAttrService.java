package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.BaseSaleAttrEntity;

import java.util.Map;

/**
 * 
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
public interface BaseSaleAttrService extends IService<BaseSaleAttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

