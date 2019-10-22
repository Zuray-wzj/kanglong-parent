package com.it18zhang.kanglong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class KanglongCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanglongCartApplication.class, args);
    }

}
