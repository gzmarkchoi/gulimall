package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.BaseAttrValueEntity;

import java.util.Map;

/**
 * 属性值表
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
public interface BaseAttrValueService extends IService<BaseAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

