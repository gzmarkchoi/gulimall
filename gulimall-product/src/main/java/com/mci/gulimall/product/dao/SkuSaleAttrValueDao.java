package com.mci.gulimall.product.dao;

import com.mci.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mci.gulimall.product.vo.SkuItemSalesAttrsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 15:50:34
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<SkuItemSalesAttrsVo> getSaleAttrsBySpuId(@Param("spuId") Long spuId);
}
