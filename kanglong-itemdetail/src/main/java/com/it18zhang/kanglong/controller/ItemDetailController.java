package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.service.FileService;
import com.it18zhang.kanglong.service.ItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 商品详情控制器
 */
@Controller
@RequestMapping("/item")
public class ItemDetailController {

	@Autowired
	private ItemDetailService itemDetailService ;

	@Autowired
	private FileService fileService;

	/**
	 *
	 */
	@GetMapping("{id}.html")
	public String toPage(@PathVariable("id") Long id , Model model){
		//查询商品的所有信息
		Map<String,Object> map = itemDetailService.loadModel(id) ;

		//生成静态页
		fileService.genHtml(map , id);

		// 添加模型到model中，为了进行thymeleaf渲染。
		model.addAllAttributes(map) ;

		return "item" ;
	}

	@GetMapping("/xxx")
	public String toPage1(){


		return "item" ;
	}
}
