package com.it18zhang.kanglong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.it18zhang.kanglong.mapper")
public class KanglongUserApplication {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(KanglongUserApplication.class, args);
//		StringRedisTemplate t = ac.getBean(StringRedisTemplate.class) ;
//		t.boundValueOps("key2").set("1234" , 20 , TimeUnit.SECONDS);
	}

}
