package com.it18zhang.kanglong;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 *
 */
@SpringBootApplication
@MapperScan("com.it18zhang.kanglong.mapper")
@EnableDiscoveryClient

public class KangLongServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(KangLongServiceApplication.class, args);
	}
}
