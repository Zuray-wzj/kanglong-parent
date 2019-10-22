package com.it18zhang.kanglong;

import com.it18zhang.kanglong.common.entity.Spu;
import com.it18zhang.kanglong.service.SpuService;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

/**
 *
 */
@SpringBootApplication
@MapperScan("com.it18zhang.kanglong.mapper")
@EnableDiscoveryClient

public class KangLongServiceApplication {
	public static void main(String[] args) {
//		ApplicationContext ac=SpringApplication.run(KangLongServiceApplication.class);
//		SpuService s=ac.getBean(SpuService.class);
//		Spu spu=s.findById(2L);
//		s.saveOrUpdate(spu);

		SpringApplication.run(KangLongServiceApplication.class, args);
	}
}
