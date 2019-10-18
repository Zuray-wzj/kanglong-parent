package com.it18zhang.kanglong.search.service;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.entity.Item;
import com.it18zhang.kanglong.common.vo.PageResult;
import com.it18zhang.kanglong.common.vo.SpecParamVO;
import com.it18zhang.kanglong.search.dao.ItemRepository;
import com.it18zhang.kanglong.search.domain.SearchRequest;
import com.it18zhang.kanglong.search.domain.SearchResult;
import com.it18zhang.kanglong.service.api.BrandServiceApi;
import com.it18zhang.kanglong.service.api.CategoryServiceApi;
import com.it18zhang.kanglong.service.api.SpecParamServiceApi;
import com.it18zhang.kanglong.service.api.SpuServiceApi;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *ItemService
 */
@Service
public class SearchService {
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	public SpuServiceApi spuServiceApi ;

	@Autowired
	private ElasticsearchTemplate t ;

	@Autowired
	private CategoryServiceApi categoryServiceApi ;

	@Autowired
	private BrandServiceApi brandServiceApi ;

	@Autowired
	private SpecParamServiceApi specParamServiceApi ;

	/**
	 * 进行搜索
	 */
	public SearchResult search(SearchRequest req){
		//无搜索内容
		if(StringUtils.isEmpty(req.getKey())){
			return null ;
		}

		NativeSearchQueryBuilder qb = new NativeSearchQueryBuilder();
		//all
		BoolQueryBuilder allQuery = QueryBuilders.boolQuery() ;
		allQuery.must(QueryBuilders.matchQuery("all" , req.getKey())) ;


		//filter
		Map<String,String> filter = req.getFilter() ;
		for(Map.Entry<String,String> entry : filter.entrySet()){
			String key = entry.getKey() ;
			String value = entry.getValue() ;
			if(!"cid3".equals(key) && !"brandId".equals(key)){
				key = "spec." + key + ".keyword" ;
			}
			allQuery.filter(QueryBuilders.termsQuery(key , value)) ;
		}
		qb.withQuery(allQuery);

		//分页处理
		qb.withPageable(PageRequest.of(req.getPage() - 1 , req.DEFAULT_SIZE)) ;

		//结果字段的过滤
		qb.withSourceFilter(new FetchSourceFilter(new String[]{"id" , "skus" ,"subTitle"} , null)) ;

		//聚合操作
		String agg_category = "agg_category" ;
		String agg_brand = "agg_brand" ;
		qb.addAggregation(AggregationBuilders.terms(agg_category).field("cid3")) ;
		qb.addAggregation(AggregationBuilders.terms(agg_brand).field("brandId")) ;

		//执行查询
		AggregatedPage<Item> page = t.queryForPage(qb.build() , Item.class) ;

		//得到总记录数
		long count = page.getTotalElements() ;
		//得到总页数
		long pages = page.getTotalPages() ;

		//搜索的商品
		List<Item> items = page.getContent() ;

		//得到聚合集合
		Aggregations aggs = page.getAggregations() ;

		//提取品类ids
		List<Long> category_ids = getIdsFromLongTerms(aggs.get(agg_category)) ;
		List<Category> categories = categoryServiceApi.findByIds(category_ids.toArray(new Long[category_ids.size()])) ;

		//提取品牌的ids
		List<Long> brand_ids = getIdsFromLongTerms(aggs.get(agg_brand)) ;
		List<Brand> brands = brandServiceApi.findByIds(brand_ids.toArray(new Long[brand_ids.size()])).getBody() ;

		//对规格参数进行过滤，只有一个品类的情况下
		List<Map<String, Object>> specAggList = null ;
		if(categories!= null && categories.size() == 1){
			NativeSearchQueryBuilder qb2 = new NativeSearchQueryBuilder() ;
			qb2.withQuery(allQuery) ;
			//提取规格
			List<SpecParamVO> params = specParamServiceApi.findByCid(categories.get(0).getId()) ;
			for(SpecParamVO vo : params){
				String specParamName = vo.getName() ;
				//添加规格参数的聚合查询
				qb2.addAggregation(AggregationBuilders.terms(specParamName).field("spec." + specParamName + ".keyword")) ;
			}
			page = t.queryForPage(qb2.build() , Item.class) ;
			aggs = page.getAggregations();
			Map<String , Aggregation> aggMap = aggs.asMap() ;

			specAggList = new ArrayList<Map<String, Object>>() ;
			//迭代所有规格参数
			for(Map.Entry<String,Aggregation> entry : aggMap.entrySet()){
				//规格参数
				String paramName = entry.getKey() ;
				StringTerms value = aggs.get(paramName) ;
				List<StringTerms.Bucket> list =value.getBuckets() ;
				//选项值集合
				List<String> options = new ArrayList<String>() ;
				for(StringTerms.Bucket b : list){
					options.add(b.getKeyAsString()) ;
				}
				Map<String,Object> map = new HashMap<String,Object>() ;
				map.put("k" , paramName) ;
				map.put("options" , options) ;
				specAggList.add(map) ;
			}
		}

		//返回搜索结果
		return new SearchResult(count , pages , items , categories , brands , specAggList) ;
	}

	/**
	 * 提取分组后的每个组id形成集合
	 */
	private List<Long> getIdsFromLongTerms(LongTerms terms){
		List<Long> ids = new ArrayList<Long>();
		for (LongTerms.Bucket bucket : terms.getBuckets()) {
			Long cid = bucket.getKeyAsNumber().longValue();
			ids.add(cid);
		}
		return ids;
	}
}
