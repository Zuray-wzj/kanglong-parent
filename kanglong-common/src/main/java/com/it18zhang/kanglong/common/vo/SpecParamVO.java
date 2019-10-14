package com.it18zhang.kanglong.common.vo;

import lombok.Data;

/**
 * 规格参数VO
 */
@Data
public class SpecParamVO {
	private Long id;            //id
	private Long cid;           //所属分类
	private String cname;       //所属分类

	private Long groupId;       //规格组id
	private String groupName;   //规格组id

	private String name;        //名称
	private Boolean numeric;    //是否是数字类型
	private String unit;        //单位
	private Boolean generic;    //是否是常规参数
	private Boolean searching;  //是否可搜索
	private String segments;    //段信息,选项
}
