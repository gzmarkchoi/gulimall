package com.mci.gulimall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.mci.common.to.es.SkuEsModel;
import com.mci.gulimall.search.config.GulimallElasticSearchConfig;
import com.mci.gulimall.search.constant.EsConstant;
import com.mci.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        // save to es
        // 1. create index in es, setup mapping for product

        // 2. save data in es
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel model : skuEsModels) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String source = JSON.toJSONString(model);
            indexRequest.source(source, XContentType.JSON);

            bulkRequest.add(indexRequest);
        }

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

        boolean hasFailures = bulk.hasFailures();

        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());

        log.info("Product status up completed: {}, return data: {}", collect, bulk.toString());

        return hasFailures;
    }
}
