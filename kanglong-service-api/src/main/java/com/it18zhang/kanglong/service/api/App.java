package com.it18zhang.kanglong.service.api;

import com.it18zhang.kanglong.common.entity.Brand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

/**
 * App
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
public class App {
	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(App.class,args) ;
		BrandServiceApi brandServiceApi = ac.getBean(BrandServiceApi.class) ;
		Brand b = brandServiceApi.findById(1115L).getBody() ;
		System.out.println(b.getName());
	}
}
