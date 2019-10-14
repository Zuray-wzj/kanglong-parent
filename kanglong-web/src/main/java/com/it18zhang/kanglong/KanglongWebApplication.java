package com.it18zhang.kanglong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class KanglongWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanglongWebApplication.class, args);
    }

}

