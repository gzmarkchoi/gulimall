package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 15:50:34
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

