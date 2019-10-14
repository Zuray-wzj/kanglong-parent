package com.it18zhang.kanglong.common.vo;

import lombok.Data;

/**
 * CategoryVO
 */
@Data
public class CategoryVO {
	private Long id;        //id
	private String c1name;    //类名
	private String c2name;    //类名
	private String c3name;    //类名
	private boolean leaf;   //是否是最后一级品类
	private String fullname;  //类名
	private Integer idx;    //排序值
}
