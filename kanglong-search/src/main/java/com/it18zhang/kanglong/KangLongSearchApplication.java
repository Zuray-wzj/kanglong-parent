package com.it18zhang.kanglong.search;

import com.it18zhang.kanglong.search.controller.SearchController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.it18zhang.kanglong"})
public class KangLongSearchApplication {
	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(KangLongSearchApplication.class, args);
		SearchController proxy = ac.getBean(SearchController.class) ;
		System.out.println();
	}
}
