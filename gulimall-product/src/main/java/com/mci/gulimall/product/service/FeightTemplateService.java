package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.FeightTemplateEntity;

import java.util.Map;

/**
 * 运费模版
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
public interface FeightTemplateService extends IService<FeightTemplateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

