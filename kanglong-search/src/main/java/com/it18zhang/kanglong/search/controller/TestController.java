package com.it18zhang.kanglong.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/hello")
	@ResponseBody
	public String sayHi() {
		return "hello world";
	}
}
