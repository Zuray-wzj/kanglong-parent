package com.it18zhang.kanglong.common.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 订单明细类
 */
@Data
@Table(name="t_order_detail")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;    		//'订单详情id ',

	@Column(name = "order_id")
	private Long orderId;   	//'订单id',
	@Column(name = "sku_id")
	private Long skuId;    		//'sku商品id',
	private Integer num;    	//'购买数量',
	private String title;    	//'商品标题',
	@Column(name = "own_spec")
	private String ownSpec;    	//'商品动态属性键值集',
	private Long price;    		//'价格,单位：分',
	private String image;		//'商品图片',
}
