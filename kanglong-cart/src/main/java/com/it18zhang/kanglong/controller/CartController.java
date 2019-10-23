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

	@GetMapping("/add11")
	public void addCart22() throws Exception{
		Cart cart=new Cart();
		cart.setPrice(1199L);
		cart.setOwnSpec("{\"机身颜色\":\"金\",\"内存\":\"4GB\",\"机身存储\":\"32GB\"}");
		cart.setImage("http://localhost:63342/kanglong-parent/kanglong-portal/images/12/15/1524297315534.jpg");
		cart.setNum(1);
		cart.setUserId(29L);
		cart.setSkuId(2868393L);
		cart.setTitle("三星 Galaxy C5（SM-C5000）4GB+32GB ");

String token="IfomZxdgF9ce/BKCfigeXMaqpTzl4onIrr97FmKWd5udj/cT15xxGBFg2VSyzu4oxxH95IZ08qOjyPSn2toHxdf3qjqnxxBf3cnUnke6E1u8g8zhgDfAtP3wixdLxdlit56xxoAidIxxUyIkWwFnmftwvo11hgnWz0eM3Z9tppa5uCRnvTTExkx";
		cartService.addCart(cart ,token) ;
		System.out.println("uefsdfbsjdfsjkdgse83348394328423874239842");
	}

	@GetMapping("/findcarts")
	public Map<String,Cart> findCarts(@RequestParam(value = "token", defaultValue = "")  String token) throws Exception {
		//Long userId = JWTUtil.parseUserIdFromToken(token) ;
		return cartService.findCartsByUserId(29L) ;
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
