package com.it18zhang.kanglong.common.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 订单类
 */
@Data
@Table(name="t_order")
public class Order {
	//id使用雪花算法实现的
	@Id
	private Long order_id;    		//'订单id',
	private Long total_pay;    		//'总金额，单位为分',
	private Long actual_pay = 0L;  	//'实付金额。单位:分。如:20007，表示:200元7分',
	private Long post_fee;    		//'邮费。单位:分。如:20007，表示:200元7分',
	private Long user_id;    		//'用户id',

	private String buyer_nick;    	//'买家昵称',
	private boolean buyer_rate;    	//'买家是否已经评价,0未评价，1已评价',
	private String receiver;    	//'收货人',
	private Integer source_type = 1;   	//'订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端',
	private String promotion_ids; 	//
	private Integer payment_type = 1;  	//'支付类型，1、在线支付，2、货到付款',
	private Date create_time;   	//
	private String shipping_name; 	//'物流名称',
	private String shipping_code; 	//'物流单号',
	private String buyer_message; 	//'买家留言',
	private String receiver_state;	//'收获地址（省）',
	private String receiver_city; 	//'收获地址（市）',
	private String receiver_zip;  	//'发票类型(0无发票1普通发票，2电子发票，3增值税发票)',
	private Integer invoice_type = 1;  	//'收货人邮编',
	private String receiver_district;	//'收获地址（区/县）',
	private String receiver_address; 	//'收获地址（街道、住址等详细地址）',
	private String receiver_mobile;  	//'收货人手机',
	@Transient
	private List<OrderDetail> orderDetails ;	//
}
