package com.mci.gulimall.search.vo;

import com.mci.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    private List<SkuEsModel> products;

    private Integer pageNum;

    private Long totalRecord;

    private Integer totalPages;

    private List<BrandVo> brands;
    private List<AttrVo> attrs;

    @Data
    public static class BrandVo {
        private Long brandId;

        private String brandName;

        private String brandImg;
    }

    public static class CatalogVo {

    }

    @Data
    public static class AttrVo {
        private Long attrId;

        private String attrName;

        private List<String> attrValue;
    }
}
