package com.it18zhang.kanglong.search.controller;

import com.it18zhang.kanglong.common.entity.Item;
import com.it18zhang.kanglong.common.vo.PageResult;
import com.it18zhang.kanglong.search.domain.SearchRequest;
import com.it18zhang.kanglong.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 搜索控制器
 */
//@Controller
//@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService ;

	@PostMapping("/page")
	@ResponseBody
	public PageResult<Item> search(@RequestBody SearchRequest req) {
		return searchService.search(req) ;
	}
}
