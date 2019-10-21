package com.it18zhang.kanglong;

import com.it18zhang.kanglong.service.ItemDetailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.Map;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class KanglongItemdetailApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanglongItemdetailApplication.class, args);
//		ItemDetailService s = SpringApplication.run(KanglongItemdetailApplication.class, args).getBean(ItemDetailService.class);
//		Map<String,Object> map = s.loadModel(2L) ;
//
//		System.out.println();
    }

}
