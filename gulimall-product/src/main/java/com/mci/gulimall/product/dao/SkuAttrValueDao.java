package com.mci.gulimall.product.dao;

import com.mci.gulimall.product.entity.SkuAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * sku平台属性值关联表
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
@Mapper
public interface SkuAttrValueDao extends BaseMapper<SkuAttrValueEntity> {
	
}
