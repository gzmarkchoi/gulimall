package com.mci.gulimall.search;

import com.alibaba.fastjson.JSON;
import com.mci.gulimall.search.config.GulimallElasticSearchConfig;
import lombok.Data;
import lombok.ToString;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void contextLoads() {
        System.out.println(client);
    }

    /**
     * insert data and update data
     */
    @Test
    public void indexData() throws IOException {
        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("1");

        User user = new User();
        user.setUserName("Christopher");
        user.setAge(18);
        user.setGender("male");

        String jsonString = JSON.toJSONString(user);
        indexRequest.source(jsonString, XContentType.JSON);

        IndexResponse index = client.index(indexRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(index);
    }

    @Data
    class User {
        private String userName;
        private String gender;
        private Integer age;
    }

    @Test
    public void searchData() throws IOException {
        // 1. create search request
        SearchRequest searchRequest = new SearchRequest();
        // set index
        searchRequest.indices("bank");
        // set DSL, search conditions
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 1.1 search conditions
        sourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));

        // 1.2 age aggregation
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
        sourceBuilder.aggregation(ageAgg);

        // 1.3 salary average aggregation
        AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");
        sourceBuilder.aggregation(balanceAvg);

//        sourceBuilder.from();
//        sourceBuilder.size();
//        sourceBuilder.aggregation();
        System.out.println("----------------- Source Builder -----------------");
        System.out.println(sourceBuilder.toString());
        System.out.println("------------------------------------");
        searchRequest.source(sourceBuilder);

        // 2. execute search
        SearchResponse searchResponse = client.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

        // 3. search response
//        System.out.println(searchResponse.toString());
//        Map map = JSON.parseObject(searchResponse.toString(), Map.class);
        // 3.1 get all hits
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            Account account = JSON.parseObject(sourceAsString, Account.class);

            System.out.println("------------------------------------");
            System.out.println("Account:" + account);
        }

        // 3.2 get analysed data
        Aggregations aggregations = searchResponse.getAggregations();
        Terms ageAgg1 = aggregations.get("ageAgg");
        for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println("Age: " + keyAsString + " ==> " + bucket.getDocCount());
        }

        Avg balanceAvg1 = aggregations.get("balanceAvg");

        System.out.println("------------------------------------");
        System.out.println("Average salary: " + balanceAvg1.getValue());
    }

    @ToString
    @Data
    static class Account {
        private Integer accountNumber;
        private Integer balance;
        private String firstName;
        private String lastName;
        private Integer age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;
    }


}
