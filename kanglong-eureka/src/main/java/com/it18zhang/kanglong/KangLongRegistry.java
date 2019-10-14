package com.it18zhang.kanglong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心
 */
@EnableEurekaServer
@SpringBootApplication
public class KangLongRegistry {
	public static void main(String[] args) {
		SpringApplication.run(KangLongRegistry.class, args);
	}
}
