package com.it18zhang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ItemService
 */
@Service
public class ItemService {

	@Autowired
	public ItemDao dao ;

	//注入ES模板
	@Autowired
	public ElasticsearchTemplate template ;

	/**
	 * 按照id查询Item
	 */
	public Optional<Item> findById(Long id){
		return dao.findById(id) ;
	}
}
