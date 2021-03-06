package com.mci.gulimall.warehouse.dao;

import com.mci.gulimall.warehouse.entity.PurchaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-03-06 22:47:52
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}
