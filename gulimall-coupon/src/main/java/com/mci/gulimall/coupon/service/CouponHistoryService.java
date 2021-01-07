package com.mci.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.coupon.entity.CouponHistoryEntity;

import java.util.Map;

/**
 * 优惠券领取历史记录
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 17:50:27
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

