package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.BaseAttrInfoEntity;

import java.util.Map;

/**
 * 属性表
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
public interface BaseAttrInfoService extends IService<BaseAttrInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

