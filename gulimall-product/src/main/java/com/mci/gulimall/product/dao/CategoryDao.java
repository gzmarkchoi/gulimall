package com.mci.gulimall.product.dao;

import com.mci.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 15:50:34
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
