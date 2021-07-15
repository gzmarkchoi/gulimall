package com.mci.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 1 - integrate MyBatis-Plus
 * 1) add dependency
 * 2) config
 * 1) config data source
 * - import driver
 * - config application.yml
 * 2) config MyBatis-Plus
 * - user @MapperScan
 * - SQL mapper files
 * <p>
 * 3) TODO
 * <p>
 * 4) Exception handling TODO
 * <p>
 * 5) module engine
 * - disable thymeleaf cache
 * - static resources in /static folder
 * - pages are in /templates folder, default web folder
 * - activate real time update (web pages) without restart the server
 * - spring-boot-devtools in pom.xml
 * - thymeleaf must be disable
 * - ctrl + shift  + F9 in Intellij
 * <p>
 * 6) integrate Redis
 * - data-redis-starter dependency
 * - Redis host, the CentOS VM
 * - use StringRedisTemplate(Spring Boot)
 * <p>
 * 7) integrate Redisson as distributed lock
 * - add dependency
 * <p>
 * 8) integrate Spring
 * - spring-boot-starter-cache dependency
 * - auto-config(CacheAutoConfiguration,RedisCacheConfiguration)
 * - manuel-config(application.properties)
 */
@EnableRedisHttpSession
@EnableFeignClients(basePackages = "com.mci.gulimall.product.feign")
@EnableDiscoveryClient
@MapperScan("com.mci.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
