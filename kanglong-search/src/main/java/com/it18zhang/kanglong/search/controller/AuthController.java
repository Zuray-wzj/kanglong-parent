package com.it18zhang.kanglong.search.controller;

import com.it18zhang.kanglong.common.entity.SearchRequest;
import com.it18zhang.kanglong.common.entity.SearchResult;
import com.it18zhang.kanglong.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器,判断是否登录
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

	@GetMapping("/verify")
	public String verify() {
		return "admin" ;
	}
}
