package com.it18zhang.kanglong.search.dao;

import com.it18zhang.kanglong.common.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 */
public interface ItemDao extends ElasticsearchRepository<Item,Long> {
}
