package com.it18zhang.kanglong.search.controller;

import com.it18zhang.kanglong.common.entity.SearchRequest;
import com.it18zhang.kanglong.common.entity.SearchResult;
import com.it18zhang.kanglong.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 搜索控制器
 */
@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService ;

	@PostMapping("/page")
	public SearchResult search(@RequestBody SearchRequest req) {
		SearchResult sr = searchService.search(req) ;
		sr.setTotal(sr.getCount());
		sr.setTotalPage(sr.getPages());
		sr.setItems(sr.getList());
		return sr ;
	}
}
