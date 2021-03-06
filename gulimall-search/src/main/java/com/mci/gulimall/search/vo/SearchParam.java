package com.mci.gulimall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * Trait all possible search parameters from index page
 */
@Data
public class SearchParam {

    private String keyword;

    private Long catalog3Id;

    private String sort;

    private Integer hasStock;

    private String skuPrice;

    private List<Long> brandIds;

    private List<String> attrs;

    // default value 1
    private Integer pageNum = 1;

    // the original query string
    private String _queryString;
}
