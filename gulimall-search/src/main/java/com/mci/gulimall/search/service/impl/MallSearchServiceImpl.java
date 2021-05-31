package com.mci.gulimall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.mci.common.to.es.SkuEsModel;
import com.mci.gulimall.search.config.GulimallElasticSearchConfig;
import com.mci.gulimall.search.constant.EsConstant;
import com.mci.gulimall.search.service.MallSearchService;
import com.mci.gulimall.search.vo.SearchParam;
import com.mci.gulimall.search.vo.SearchResult;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public SearchResult search(SearchParam searchParam) {
        SearchResult result = null;

        // 1. Prepare search request
        SearchRequest searchRequest = buildSearchRequest(searchParam);

        try {
            // 2. execute search request
            SearchResponse response = client.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

            // 3. analyse response
            result = buildSearchResult(response, searchParam);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private SearchResult buildSearchResult(SearchResponse response, SearchParam searchParam) {

        SearchResult result = new SearchResult();

        SearchHits hits = response.getHits();
        List<SkuEsModel> skuEsModelList = new ArrayList<>();

        if (hits.getHits() != null && hits.getHits().length > 0) {
            for (SearchHit hit : hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel sku = new SkuEsModel();
                SkuEsModel skuEsModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
                skuEsModelList.add(skuEsModel);
            }
        }

        // products
        result.setProducts(skuEsModelList);

        // attr
        List<SearchResult.AttrVo> attrVos = new ArrayList<>();
        ParsedNested attrAgg = response.getAggregations().get("attr_agg");

        ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attr_id_agg");
        for (Terms.Bucket attrIdAggBucket : attrIdAgg.getBuckets()) {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            long attrId = attrIdAggBucket.getKeyAsNumber().longValue();

            String attrName = ((ParsedStringTerms) attrIdAggBucket.getAggregations().get("attr_name_agg"))
                    .getBuckets().get(0).getKeyAsString();
            List<String> attrValues = ((ParsedStringTerms) attrIdAggBucket.getAggregations().get("attr_value_agg")).getBuckets().stream().map(item -> {
                String keyAsString = ((Terms.Bucket) item).getKeyAsString();

                return keyAsString;
            }).collect(Collectors.toList());

            attrVo.setAttrId(attrId);
            attrVo.setAttrName(attrName);
            attrVo.setAttrValue(attrValues);

            attrVos.add(attrVo);
        }

        result.setAttrs(attrVos);

        // brand
        List<SearchResult.BrandVo> brandVos = new ArrayList<>();
        ParsedLongTerms brandAgg = response.getAggregations().get("brand_agg");
        for (Terms.Bucket brandAggBucket : brandAgg.getBuckets()) {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();

            long brandId = brandAggBucket.getKeyAsNumber().longValue();
            String brandName = ((ParsedStringTerms) brandAggBucket.getAggregations().get("brand_name_agg"))
                    .getBuckets().get(0).getKeyAsString();
            String brandImg = ((ParsedStringTerms) brandAggBucket.getAggregations().get("brand_img_agg"))
                    .getBuckets().get(0).getKeyAsString();

            brandVo.setBrandId(brandId);
            brandVo.setBrandName(brandName);
            brandVo.setBrandImg(brandImg);

            brandVos.add(brandVo);
        }

        result.setBrands(brandVos);

        ParsedLongTerms catalogAgg = response.getAggregations().get("catalog_agg");

        // catalog
        List<SearchResult.CatalogVo> catalogVos = new ArrayList<>();
        List<? extends Terms.Bucket> catalogAggBuckets = catalogAgg.getBuckets();
        for (Terms.Bucket catalogAggBucket : catalogAggBuckets) {
            SearchResult.CatalogVo catalogVo = new SearchResult.CatalogVo();

            String keyAsString = catalogAggBucket.getKeyAsString();

            catalogVo.setCatalogId(Long.parseLong(keyAsString));
            ParsedStringTerms catalogNameAgg = catalogAggBucket.getAggregations().get("catalog_name_agg");
            String catalogName = catalogNameAgg.getBuckets().get(0).getKeyAsString();
            catalogVo.setCatalogName(catalogName);

            catalogVos.add(catalogVo);
        }

        result.setCatalogs(catalogVos);

        // page number
        result.setPageNum(searchParam.getPageNum());

        // total hits
        long totalHits = hits.getTotalHits().value;
        result.setTotalRecord(totalHits);

        // total pages
        int totalPages = (int) totalHits % EsConstant.PRODUCT_PAGESIZE == 0 ?
                (int) totalHits / EsConstant.PRODUCT_PAGESIZE : (int) (totalHits / EsConstant.PRODUCT_PAGESIZE + 1);
        result.setTotalPages(totalPages);


        return result;
    }

    private SearchRequest buildSearchRequest(SearchParam param) {

        // DSL builder
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        /**
         * Query
         */

        // keyword
        if (!StringUtils.isEmpty(param.getKeyword())) { //1.1 must
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", param.getKeyword()));
        }

        // catalog 3 id
        if (param.getCatalog3Id() != null) { // 1.2 bool - filter
            boolQuery.filter(QueryBuilders.termQuery("catalogId", param.getCatalog3Id()));
        }

        // brand id
        if (param.getBrandIds() != null && param.getBrandIds().size() > 0) { // 1.2 bool - filter
            boolQuery.filter(QueryBuilders.termQuery("brandId", param.getBrandIds()));
        }

        // attrs
        if (param.getAttrs() != null && param.getAttrs().size() > 0) {
            for (String attrStr : param.getAttrs()) {
                BoolQueryBuilder nestedBoolQueryBuilder = QueryBuilders.boolQuery();
                String[] s = attrStr.split("_");
                String attrId = s[0]; // search attr Id
                String[] attrValues = s[1].split(":");// search attr value

                nestedBoolQueryBuilder.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                nestedBoolQueryBuilder.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));

                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQueryBuilder, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }
        }

        // has stock
        boolQuery.filter(QueryBuilders.termQuery("hasStock", param.getHasStock()));

        if (!StringUtils.isEmpty(param.getSkuPrice())) {
            // 1_500 or _500 or 500_
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
            String[] strings = param.getSkuPrice().split("_");
            if (strings.length == 2) {
                // 1_500
                rangeQuery.gte(strings[0]).lte(strings[1]);
            } else if (strings.length == 1) {
                if (param.getSkuPrice().startsWith("_")) {
                    // _500
                    rangeQuery.lte(strings[0]);
                }
                if (param.getSkuPrice().endsWith("_")) {
                    // 500_
                    rangeQuery.gte(strings[0]);
                }
            }

            boolQuery.filter(rangeQuery);
        }

        sourceBuilder.query(boolQuery);

        /**
         * Sort, pagination, highlighted items
         */
        //2.1 sort
        if (!StringUtils.isEmpty(param.getSort())) {
            String sort = param.getSort();
            //sort=hotScore_asc or desc
            String[] strings = sort.split("_");
            SortOrder sortOrder = strings[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(strings[0], sortOrder);
        }

        //2.2 pagination
        sourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGESIZE);
        sourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);

        // 2.3 highlighted items
        if (!StringUtils.isEmpty(param.getKeyword())) {
            HighlightBuilder builder = new HighlightBuilder();

            builder.field("skuTitle");
            builder.preTags("<b style='color:red'>");
            builder.postTags("</b>");

            sourceBuilder.highlighter(builder);
        }

        /**
         * Aggregation
         */
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg").field("brandId").size(50);
        // sub aggregations brand
        brandAgg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brandAgg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));

        sourceBuilder.aggregation(brandAgg);

        TermsAggregationBuilder catalogAgg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20);
        // sub aggregations catalog
        catalogAgg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));

        sourceBuilder.aggregation(catalogAgg);

        // sub aggregations attr
        NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attr_agg", "attrs");
        NestedAggregationBuilder attrIdAgg = attrAgg.subAggregation(AggregationBuilders.terms("attr_id_agg").field("attrs.attrId"));
        // sub aggregations, attr name for attr Id
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        // sub aggregations, attr value for attr Id
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));

        attrAgg.subAggregation(attrIdAgg);

        sourceBuilder.aggregation(attrAgg);

        String dsl = sourceBuilder.toString();
        System.out.println("DSL query: " + dsl);

        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, sourceBuilder);

        return searchRequest;
    }
}
