package com.mci.gulimall.product.dao;

import com.mci.gulimall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mci.gulimall.product.vo.SkuItemVo;
import com.mci.gulimall.product.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 15:50:34
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
