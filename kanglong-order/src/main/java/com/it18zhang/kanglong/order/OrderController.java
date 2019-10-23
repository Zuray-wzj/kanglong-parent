package com.it18zhang.kanglong.order;

import com.it18zhang.kanglong.common.entity.Order;
import com.it18zhang.kanglong.common.util.JWTUtil;
import com.it18zhang.kanglong.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * OrderController
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService ;

	@PostMapping(value = "/submit" ,consumes = "application/json")
	public void submit(@RequestBody Order order , @RequestParam("token") String token) throws Exception {
		//解析出userId
		//Long userId = JWTUtil.parseUserIdFromToken(token) ;
		order.setUser_id(29L);
		orderService.saveOrder(order);
		System.out.println("ok");
	}
}
