package com.mci.gulimall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 1. Maven dependency
 * 2. Config class
 * 3. official documentation https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high.html
 */
@Configuration
public class GulimallElasticSearchConfig {

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024)
//        );
        COMMON_OPTIONS = builder.build();
    }

    /**
     * Inject a RestHighLevelClient into Bean
     *
     * @return
     */
    @Bean
    public RestHighLevelClient esRestClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("192.168.56.10", 9200, "http"));

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);

        return restHighLevelClient;
    }
}
