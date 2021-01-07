package com.mci.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 17:50:27
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

