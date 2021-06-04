package com.mci.gulimall.search.vo;

import com.mci.common.to.es.SkuEsModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    private List<SkuEsModel> products;

    private Integer pageNum;

    private Long totalRecord;

    private Integer totalPages;

    private List<Integer> pageNavs;

    private List<BrandVo> brands;

    private List<AttrVo> attrs;

    private List<CatalogVo> catalogs;

    // on page dynamic navigation search
    private List<NavVo> navs;

    @Data
    public static class NavVo {
        private String navName;

        private String navValue;

        private String link;
    }

    @Data
    public static class BrandVo {
        private Long brandId;

        private String brandName;

        private String brandImg;
    }

    @Data
    public static class CatalogVo {
        private Long catalogId;

        private String catalogName;
    }

    @Data
    public static class AttrVo {
        private Long attrId;

        private String attrName;

        private List<String> attrValue;
    }

}
