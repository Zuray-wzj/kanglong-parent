package com.it18zhang.kanglong.order.service;

import com.it18zhang.kanglong.common.entity.Order;
import com.it18zhang.kanglong.common.entity.OrderDetail;
import com.it18zhang.kanglong.common.util.IdWorker;
import com.it18zhang.kanglong.order.mapper.OrderDetailMapper;
import com.it18zhang.kanglong.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderService
 */
@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper ;

	@Autowired
	private OrderDetailMapper orderDetailMapper ;
	//保存订单
	public void saveOrder(Order order){
		IdWorker idWorker = new IdWorker() ;
		Long orderId = idWorker.nextId() ;
		//1.生成订单id
		order.setOrder_id(orderId);
		orderMapper.insert(order) ;

		for(OrderDetail d : order.getOrderDetails()){
			d.setOrderId(orderId);
			orderDetailMapper.insert(d) ;
		}
	}
}
