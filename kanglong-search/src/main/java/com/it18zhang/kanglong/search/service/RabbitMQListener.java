package com.it18zhang.kanglong.search.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.it18zhang.kanglong.service.api.CategoryServiceApi;
import com.it18zhang.kanglong.service.api.SpecGroupServiceApi;
import com.it18zhang.kanglong.service.api.SpecParamServiceApi;
import com.it18zhang.kanglong.service.api.SpuServiceApi;
import com.it18zhang.kanglong.common.entity.Item;
import com.it18zhang.kanglong.common.entity.Sku;
import com.it18zhang.kanglong.common.entity.Spu;
import com.it18zhang.kanglong.common.vo.SpecParamVO;
import com.it18zhang.kanglong.search.dao.ItemRepository;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * rabbitMA的监听器(消费者)
 */
@Component
public class RabbitMQListener {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate ;

	@Autowired
	private SpuServiceApi spuServiceApi ;

	@Autowired
	private ItemRepository itemRepository ;

	@Autowired
	private CategoryServiceApi categoryServiceApi ;

	@Autowired
	private SpecParamServiceApi specParamServiceApi ;

	/**
	 * 生成页面
	 */
	@RabbitListener(
			bindings = @QueueBinding(
					value = @Queue(value = "queue_update") ,
					exchange = @Exchange(value = "itemdetail.direct", type = ExchangeTypes.DIRECT) ,
					key={"update"}
			)
	)
	public void genHtml(Long id){
		System.out.println("收到rabbitMQ消息，" + id + "发生了修改！");
		Spu spu = spuServiceApi.findById(id);
		Item item = popItem(spu);
		itemRepository.save(item);
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
		List<Map<String, Object>> skuInfoList = new ArrayList<Map<String, Object>>();
		List<Long> prices = new ArrayList<Long>();
		for (Sku sku : skus) {
			//处理价格部分
			prices.add(sku.getPrice());
			Map<String, Object> skuInfo = new HashMap<String, Object>();
			skuInfo.put("id", sku.getId());
			skuInfo.put("title", sku.getTitle());
			skuInfo.put("price", sku.getPrice());
			skuInfo.put("image", sku.getImages());
			skuInfoList.add(skuInfo);
		}
		//设置价格
		item.setPrice(prices);

		//设置skus信息
		item.setSkus(JSON.toJSONString(skuInfoList));

		//规格参数
		List<SpecParamVO> list = specParamServiceApi.findByCid(spu.getCid3());    //查询cid3品类下的所有规格参数
		String genericeSpec = spu.getSpuDetail().getSpecifications();                //普通规格
		//JSONObject genericeSpecJSON = JSON.parseObject(genericeSpec);            //普通规格JSON对象

		String specialSpec = spu.getSpuDetail().getSpec_template();                //特殊规格
		//JSONObject specialSpecJSON = JSON.parseObject(genericeSpec);            //特殊规格JSON对象

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
//				} else {
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



	/**
	 * 删除静态页
	 */
	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "queue_delete"),
			exchange = @Exchange(value = "itemdetail.direct", type = ExchangeTypes.DIRECT),
			key = {"delete"}))
	public void deleteHtml(Long id){
		elasticsearchTemplate.delete(Item.class , id + "") ;
	}
}
