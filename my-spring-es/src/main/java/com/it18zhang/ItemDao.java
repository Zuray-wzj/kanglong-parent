package com.it18zhang;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * ItemDao
 */
public interface ItemDao extends ElasticsearchRepository<Item , Long> {
	//按照标题查询
	public List<Item> findByTitle(String title) ;
	//
	public List<Item> findByTitleAndPrice(String title , Long price) ;
	public List<Item> findByTitleOrPrice(String title , Long price) ;
}
