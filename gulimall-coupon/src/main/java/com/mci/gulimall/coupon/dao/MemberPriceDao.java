package com.mci.gulimall.coupon.dao;

import com.mci.gulimall.coupon.entity.MemberPriceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 17:50:27
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {
	
}
