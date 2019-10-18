package com.it18zhang.kanglong.search.service;

import com.it18zhang.kanglong.search.dao.ItemDao;
import com.it18zhang.kanglong.service.api.SpuServiceApi;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *ItemService
 */
@Service
public class ItemService {
	@Autowired
	private ItemDao itemDao ;

	@Autowired
	public SpuServiceApi spuServiceApi ;

}
