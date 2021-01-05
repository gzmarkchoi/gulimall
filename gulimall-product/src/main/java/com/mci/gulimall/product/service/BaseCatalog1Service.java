package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.BaseCatalog1Entity;

import java.util.Map;

/**
 * 一级分类表
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
public interface BaseCatalog1Service extends IService<BaseCatalog1Entity> {

    PageUtils queryPage(Map<String, Object> params);
}

