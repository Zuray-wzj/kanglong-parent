package com.it18zhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class MySpringEsApplication {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(MySpringEsApplication.class, args);
		ItemService s = ac.getBean(ItemService.class) ;
		Optional<Item> res = s.findById(2L) ;
		//有
		if(res.isPresent()){
			System.out.println(res.get().getTitle()) ;
		}
	}

}
