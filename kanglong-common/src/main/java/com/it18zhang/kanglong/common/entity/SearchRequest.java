package com.it18zhang.kanglong.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 搜索请求
 */
public class SearchRequest {
	//搜索关键字
	private String key ;
	//当前页号
	private Integer page = DEFAULT_PAGE ;
	//规格参数过滤
	private Map<String,String> filter = new HashMap<String,String>();

	//每页显示记录数
	public static final Integer DEFAULT_SIZE = 20 ;
	//默认页编号
	public static final Integer DEFAULT_PAGE = 1 ;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Map<String, String> getFilter() {
		return filter;
	}

	public void setFilter(Map<String, String> filter) {
		this.filter = filter;
	}
}
