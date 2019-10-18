package com.it18zhang.kanglong.search.dao;

import com.it18zhang.kanglong.common.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * ES中商品仓库
 */
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {
}
