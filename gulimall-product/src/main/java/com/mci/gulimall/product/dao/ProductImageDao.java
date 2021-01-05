package com.mci.gulimall.product.dao;

import com.mci.gulimall.product.entity.ProductImageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品图片表
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
@Mapper
public interface ProductImageDao extends BaseMapper<ProductImageEntity> {
	
}
