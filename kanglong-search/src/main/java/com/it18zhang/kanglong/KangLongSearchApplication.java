package com.it18zhang.kanglong;

import com.it18zhang.kanglong.common.vo.SpuVO;
import com.it18zhang.kanglong.service.api.SpuServiceApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 *
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class KangLongSearchApplication {
	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(KangLongSearchApplication.class, args);
		SpuServiceApi api = ac.getBean(SpuServiceApi.class) ;
		System.out.println(api);
		List<SpuVO> list = api.findSpus();
		System.out.println();
	}
}
