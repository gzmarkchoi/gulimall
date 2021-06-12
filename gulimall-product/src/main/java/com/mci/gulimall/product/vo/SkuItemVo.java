package com.mci.gulimall.product.vo;

import com.mci.gulimall.product.entity.SkuImagesEntity;
import com.mci.gulimall.product.entity.SkuInfoEntity;
import com.mci.gulimall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

@Data
public class SkuItemVo {
    SkuInfoEntity skuInfoEntity;

    List<SkuImagesEntity> images;

    List<SkuItemSalesAttrsVo> salesAttrs;

    SpuInfoDescEntity description;

    List<SpuItemAttrGroupVo> groupAttrs;

    @Data
    public static class SkuItemSalesAttrsVo {
        private Long attrId;

        private String attrName;

        private List<String> attrValues;
    }

    @Data
    public static class SpuItemAttrGroupVo {
        private String groupName;

        private List<SpuBaseAttrsVo> attrs;
    }

    @Data
    public static class SpuBaseAttrsVo {
        private String attrName;

        private String attrValue;
    }
}
