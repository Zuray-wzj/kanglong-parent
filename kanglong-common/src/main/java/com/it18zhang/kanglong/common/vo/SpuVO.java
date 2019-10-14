package com.it18zhang.kanglong.common.vo;

import lombok.Data;

import java.util.Date;

/**
 * SpuVO类
 */
@Data
public class SpuVO {
	private Long id;            //
	private Long brandId;      //属于的品牌id
	private Long cid1;          //1级类目id
	private Long cid2;          //2级类目id
	private Long cid3;          //3级类目id
	private String title;       //标题
	private String subTitle;    //子标题
	private Boolean salable;    //是否可出售
	private Boolean valid;      //是否有效，逻辑删除用
	private Date createTime;    //创建时间
	private Date lastUpdateTime;//最后修改时间

	private String fullName ;	//品类全名
	private String brandName ;	//品牌名称
}
