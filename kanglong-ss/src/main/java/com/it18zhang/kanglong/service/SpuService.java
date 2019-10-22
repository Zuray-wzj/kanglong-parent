package com.it18zhang.kanglong.service;

import com.it18zhang.kanglong.common.entity.Sku;
import com.it18zhang.kanglong.common.entity.Spu;
import com.it18zhang.kanglong.common.entity.SpuDetail;
import com.it18zhang.kanglong.common.vo.SpuVO;
import com.it18zhang.kanglong.mapper.SkuMapper;
import com.it18zhang.kanglong.mapper.SpuDetailMapper;
import com.it18zhang.kanglong.mapper.SpuMapper;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品服务
 */
@Service
public class SpuService {

	@Autowired
	private SpuMapper spuMapper ;
	@Autowired
	private SpuDetailMapper spuDetailMapper;
	@Autowired
	private SkuMapper skuMapper ;

	@Autowired
	private CategoryService categoryService ;


	@Autowired
	private RabbitMessagingTemplate rabbitMessagingTemplate ;








	/**
	 * 查询商品列表
	 */
	public List<SpuVO> findSpus(){
		return spuMapper.findSpus() ;
	}

	/**
	 * 保存或更新Spu
	 */
	@Transactional
	public void saveOrUpdate(Spu spu) {
		Long cid2 = categoryService.findById(spu.getCid3()).getParentId() ;
		Long cid1 = categoryService.findById(cid2).getParentId() ;
		spu.setCid1(cid1);
		spu.setCid2(cid2);

		//插入
		if(spu.getId() == null){
			spuMapper.insert(spu) ;
			//detail
			SpuDetail detail = spu.getSpuDetail() ;
			detail.setSpuId(spu.getId());
			spuDetailMapper.insert(detail) ;

			//保存sku集合
			for(Sku sku : spu.getSkus()){
				sku.setSpuId(spu.getId());
				skuMapper.insert(sku) ;
			}
		}
		//更新
		else{
			spuMapper.updateByPrimaryKey(spu) ;
			//detail
			SpuDetail detail = spu.getSpuDetail() ;
			detail.setSpuId(spu.getId());
			spuDetailMapper.updateByPrimaryKey(detail) ;
			//skus
			for(Sku sku : spu.getSkus()){
				sku.setSpuId(spu.getId());
				if(sku.getId() == null){
					skuMapper.insert(sku) ;
				}
				else{
					skuMapper.updateByPrimaryKey(sku) ;
				}
			}
		}

		try {
			//发送消息给rabbitMQ的更新队列
			System.out.println("向RabbitMQ发送消息，修改了" + spu.getId() + "号商品!!");
			String exchange = "itemdetail.direct" ;
			String routingKey = "update" ;
//			Message msg = new Message((spu.getBrandId()+ "").getBytes() , new MessageProperties()) ;
			GenericMessage<Long> msg = new GenericMessage<Long>(spu.getId()) ;

			rabbitMessagingTemplate.send(exchange,routingKey , msg);
			System.out.println("消息发送成功!!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("消息发送出错了！！！！");
		}
	}

	/**
	 * 删除商品
	 */
	public void deleteById(Long id){
		skuMapper.deleteBySpuId(id) ;
		spuDetailMapper.deleteBySpuId(id) ;
		spuMapper.deleteByPrimaryKey(id) ;
		try {

			//发送消息给rabbitMQ的删除队列
			String exchange = "itemdetail.direct";
			String routingKey = "delete";
			rabbitMessagingTemplate.convertAndSend(exchange, routingKey, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按照id查询spu
	 */
	public Spu findById(Long id) {
		//查询spu
		Spu spu = spuMapper.selectByPrimaryKey(id) ;
		//查询detail
		SpuDetail detail = spuDetailMapper.selectByPrimaryKey(id) ;
		//按照spuid查询sku
		Sku sku = new Sku() ;
		sku.setSpuId(id);

		//组装集合
		List<Sku> list = skuMapper.select(sku) ;
		spu.setSpuDetail(detail);
		spu.setSkus(list);
		return spu ;
	}

	/**
	 * 按照skuid查询sku
	 */
	public Sku findSkuBySkuId(Long skuId) {
		return skuMapper.selectByPrimaryKey(skuId) ;
	}
}


