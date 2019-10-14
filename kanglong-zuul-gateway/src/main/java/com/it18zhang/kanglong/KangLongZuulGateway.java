package com.it18zhang.kanglong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * zuul网关
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class KangLongZuulGateway {
	public static void main(String[] args) {
		SpringApplication.run(KangLongZuulGateway.class ,args) ;
	}
}
