package com.mci.gulimall.coupon.dao;

import com.mci.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 17:50:27
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
