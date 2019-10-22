package com.it18zhang.kanglong.service;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * rabbitMA的监听器(消费者)
 */
@Component
public class RabbitMQListener {

	@Autowired
	private ItemDetailService itemDetailService ;

	@Autowired
	private FileService fileService ;

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
		Map<String, Object> map = itemDetailService.loadModel(id);
		//生成静态页
		fileService.genHtml(map, id);
	}

	/**
	 * 删除静态页
	 */
	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "queue_delete"),
			exchange = @Exchange(value = "itemdetail.direct", type = ExchangeTypes.DIRECT),
			key = {"delete"}))
	public void deleteHtml(Long id){
		fileService.deleteHtml(id) ;
	}
}
