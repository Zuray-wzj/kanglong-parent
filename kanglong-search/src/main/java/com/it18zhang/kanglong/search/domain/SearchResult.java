package com.it18zhang.kanglong.search.domain;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.entity.Item;
import com.it18zhang.kanglong.common.vo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/21.
 */
public class SearchResult extends PageResult<Item> {
	private List<Category> categories;
	private List<Brand> brands;
	private List<Map<String, Object>> specs;

	public SearchResult(Long total,
						Long pages,
						List<Item> items,
						List<Category> categories,
						List<Brand> brands,
						List<Map<String, Object>> specs) {
		super(total , pages, items) ;
		this.categories = categories ;
		this.brands = brands ;
		this.specs = specs ;
	}


}
