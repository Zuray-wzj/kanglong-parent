package com.it18zhang.kanglong.controller;

import com.alibaba.fastjson.JSON;
import com.it18zhang.kanglong.common.entity.Cart;
import com.it18zhang.kanglong.common.entity.Order;
import com.it18zhang.kanglong.common.util.JWTUtil;
import com.it18zhang.kanglong.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 购物车
 */
@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService ;
	/**
	 * 添加到购物车
	 */
	@PostMapping("/add")
	public void addCart(@RequestBody Cart cart ,@RequestParam(value = "token" , defaultValue = "") String token) throws Exception{
		cartService.addCart(cart ,token) ;
	}

	@GetMapping("/findcarts")
	public Map<String,Cart> findCarts(@RequestParam(value = "token", defaultValue = "")  String token) throws Exception {
		Long userId = JWTUtil.parseUserIdFromToken(token) ;
		return cartService.findCartsByUserId(userId) ;
	}

	@PostMapping(value = "/merge" ,consumes = "application/json")
	public void mergeCarts(@RequestParam(value = "token" ,defaultValue = "") String token , @RequestBody List<Cart> carts) throws Exception {
		Long userId = JWTUtil.parseUserIdFromToken(token);
		cartService.mergeCarts(carts ,userId);
	}

	@PostMapping(value = "/submit" ,consumes = "application/json")
	public void submitOrder(@RequestBody Order order) throws Exception {
		System.out.println("");
	}
}
