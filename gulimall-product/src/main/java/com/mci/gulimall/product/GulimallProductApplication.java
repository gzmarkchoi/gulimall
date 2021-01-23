package com.mci.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 1 - integrate MyBatis-Plus
 * 	1) add dependency
 * 	2) config
 * 		1) config data source
 * 			- import driver
 * 			- config application.yml
 * 		2) config MyBatis-Plus
 * 			- user @MapperScan
 * 			- SQL mapper files
 *
 */
@EnableDiscoveryClient
@MapperScan("com.mci.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallProductApplication.class, args);
	}

}
