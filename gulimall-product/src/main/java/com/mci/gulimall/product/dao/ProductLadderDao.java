package com.mci.gulimall.product.dao;

import com.mci.gulimall.product.entity.ProductLadderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品阶梯价格表(只针对同商品)
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
@Mapper
public interface ProductLadderDao extends BaseMapper<ProductLadderEntity> {
	
}
