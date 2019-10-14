package com.it18zhang.kanglong.common.entity;

import lombok.Data;

/**
 * 购物车
 */
@Data
public class Cart {
	private Long userId;
	private Long skuId;
	private String title;
	private String image;
	private Long price;
	private Integer num;
	private String ownSpec;
}
