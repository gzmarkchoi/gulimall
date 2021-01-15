package com.mci.gulimall.order.dao;

import com.mci.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 18:22:07
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
