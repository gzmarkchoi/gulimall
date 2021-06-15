package com.mci.gulimall.product.vo;

import com.mci.gulimall.product.entity.SkuImagesEntity;
import com.mci.gulimall.product.entity.SkuInfoEntity;
import com.mci.gulimall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

@Data
public class SkuItemVo {
    SkuInfoEntity skuInfo;

    List<SkuImagesEntity> images;

    List<SkuItemSalesAttrsVo> saleAttrs;

    SpuInfoDescEntity description;

    List<SpuItemAttrGroupVo> groupAttrs;

}
