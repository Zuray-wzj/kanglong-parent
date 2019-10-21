package com.it18zhang.kanglong.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.it18zhang.kanglong.common.entity.Item;
//import com.it18zhang.kanglong.KangLongSearchApplication;
import com.it18zhang.kanglong.common.entity.Sku;
import com.it18zhang.kanglong.common.entity.Spu;
import com.it18zhang.kanglong.common.vo.SpecParamVO;
import com.it18zhang.kanglong.common.vo.SpuVO;
import com.it18zhang.kanglong.search.KangLongSearchApplication;
import com.it18zhang.kanglong.search.dao.ItemRepository;

import com.it18zhang.kanglong.search.domain.SearchRequest;
import com.it18zhang.kanglong.search.domain.SearchResult;
import com.it18zhang.kanglong.search.service.SearchService;

import com.it18zhang.kanglong.service.api.CategoryServiceApi;
import com.it18zhang.kanglong.service.api.SpecParamServiceApi;
import com.it18zhang.kanglong.service.api.SpuServiceApi;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KangLongSearchApplication.class)
public class TestSearch {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private SearchService searchService;

	@Autowired
	ElasticsearchTemplate t;

	//Spu远程查询
	@Autowired
	private SpuServiceApi spuServiceApi;

	//品类Api查询
	@Autowired
	private CategoryServiceApi categoryServiceApi;

	//规格参数
	@Autowired
	private SpecParamServiceApi specParamServiceApi;

	//创建索引库
	@Test
	public void createIndex() {
		t.createIndex(Item.class);
		t.putMapping(Item.class) ;
	}

	//测试查询
	@Test
	public void testQuery() {
		System.out.println(spuServiceApi);
		List<SpuVO> list = spuServiceApi.findSpus();
		System.out.println();
	}

	/**
	 * 导出spu信息到es库
	 */
	@Test
	public void exportAllSpu() {
		List<SpuVO> spuvos = spuServiceApi.findSpus() ;

		for(SpuVO vo : spuvos){
			Long spuid = vo.getId() ;
			Spu spu = spuServiceApi.findById(spuid) ;
			Item item = popItem(spu) ;
			itemRepository.save(item) ;
		}
		System.out.println();
	}



	/**
	 * 组装spu对象成Item type
	 */
	public Item popItem(Spu spu) {
		Item item = new Item();
		//id
		item.setId(spu.getId());
		//品牌id
		item.setBrandId(spu.getBrandId());
		//品类
		item.setCid1(spu.getCid1());
		item.setCid2(spu.getCid2());
		item.setCid3(spu.getCid3());
		item.setCreateTime(spu.getCreateTime());
		//子标题
		item.setSubTitle(spu.getSubTitle());

		//all : spu的title 通信 移动 手机
		String cid1name = categoryServiceApi.findById(spu.getCid1()).getName();
		String cid2name = categoryServiceApi.findById(spu.getCid2()).getName();
		String cid3name = categoryServiceApi.findById(spu.getCid3()).getName();
		item.setAll(spu.getTitle() + " " + cid1name + " " + cid2name + " " + cid3name);


		//所有sku的价格
		List<Sku> skus = spu.getSkus();
		//sku的关键属性集合
		List<Map<String,Object>> skuInfoList = new ArrayList<Map<String, Object>>() ;
		List<Long> prices = new ArrayList<Long>();
		for (Sku sku : skus) {
			//处理价格部分
			prices.add(sku.getPrice());
			Map<String,Object> skuInfo = new HashMap<String, Object>() ;
			skuInfo.put("id" , sku.getId()) ;
			skuInfo.put("title" , sku.getTitle()) ;
			skuInfo.put("price" , sku.getPrice()) ;
			skuInfo.put("image" , sku.getImages()) ;
			skuInfoList.add(skuInfo) ;
		}
		//设置价格
		item.setPrice(prices);

		//设置skus信息
		item.setSkus(JSON.toJSONString(skuInfoList));

		//规格参数
		List<SpecParamVO> list = specParamServiceApi.findByCid(spu.getCid3());    //查询cid3品类下的所有规格参数
		//String genericeSpec = spu.getSpuDetail().getGenericSpec();                //普通规格


		//String genericeSpec = spu.getSpuDetail().getSpecifications();
		//JSONObject genericeSpecJSON = JSON.parseObject(genericeSpec);            //普通规格JSON对象


		//String specialSpec = spu.getSpuDetail().getSpecialSpec();                //特殊规格

		//String specialSpec = spu.getSpuDetail().getSpec_template();

		//JSONObject specialSpecJSON = JSON.parseObject(specialSpec);            //特殊规格JSON对象

		Map<String, Object> spuSpecs = new HashMap<String, Object>();

//		for (SpecParamVO vo : list) {
//			//判断是否是可搜索规格参数
//			if (vo.getSearching()) {
//				JSONObject jsonTmp = vo.getGeneric() ? genericeSpecJSON : specialSpecJSON;
//				//是否是数字
//				if (vo.getNumeric()) {
//					//是否待选值
//					if (StringUtils.isNotEmpty(vo.getSegments())) {
//						//取出规格参数值在待选值的值
//						try {
//							double value = jsonTmp.getDouble(vo.getId().toString());
//							String seg = chooseSegment(value, vo);
//							spuSpecs.put(vo.getName(), seg);
//						} catch (Exception e) {
//							//e.printStackTrace();
//							spuSpecs.put(vo.getName(), jsonTmp.getString(vo.getId().toString()));
//						}
//					} else {
//						spuSpecs.put(vo.getName(), jsonTmp.getString(vo.getId().toString()));
//					}
//				}
//				else{
//					spuSpecs.put(vo.getName(), jsonTmp.getString(vo.getId().toString()));
//				}
//			}
//		}
		item.setSpecs(spuSpecs);
		//skus
		return item;
	}

	/**
	 * 换算值位于字符串中的指定段
	 */
	private String chooseSegment(double value, SpecParamVO param) {
		String[] arr = param.getSegments().split(",");
		if (ArrayUtils.isNotEmpty(arr)) {
			for (int i = 0; i < arr.length; i++) {
				String[] arrr = arr[i].split("-");
				if (ArrayUtils.isNotEmpty(arrr)) {
					if (arrr.length == 2) {
						double low = Double.parseDouble(arrr[0]);
						double high = Double.parseDouble(arrr[1]);
						if (value < high && value >= low) {
							return arr[i];
						}
					} else {
						return arrr[0] + param.getUnit() + "以上";
					}
				}
			}
		}
		return "";
	}

	@Test
	public void aggFind(){
		NativeSearchQueryBuilder qb = new NativeSearchQueryBuilder() ;
		qb.withQuery(QueryBuilders.matchQuery("all" , "手机").operator(Operator.AND)) ;
		qb.withSourceFilter(new FetchSourceFilter(new String[]{"id" , "skus" , "subTitle"}, null)) ;
		qb.addAggregation(AggregationBuilders.terms("category").field("cid3")) ;
		qb.addAggregation(AggregationBuilders.terms("brand").field("brandId")) ;
		qb.withPageable(PageRequest.of(0 , 3)) ;
		qb.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC)) ;
		AggregatedPage<Item> page = (AggregatedPage<Item>) itemRepository.search(qb.build());
		System.out.println();
	}

	@Test
	public void testSearchService(){
		SearchRequest req = new SearchRequest() ;
		//SearchRequest req = new SearchRequest() ;
		req.setKey("手机");
		req.setPage(1);
		//SearchResult res = searchService.search(req) ;
		System.out.println();
	}
}
