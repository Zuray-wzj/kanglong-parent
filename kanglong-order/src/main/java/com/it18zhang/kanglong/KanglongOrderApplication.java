package com.it18zhang.kanglong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.it18zhang.kanglong.order.mapper")
public class KanglongOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanglongOrderApplication.class, args);
    }
}
