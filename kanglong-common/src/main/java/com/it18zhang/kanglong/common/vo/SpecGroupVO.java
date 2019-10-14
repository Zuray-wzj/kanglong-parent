package com.it18zhang.kanglong.common.vo;

import lombok.Data;

/**
 * CategoryVO
 */
@Data
public class SpecGroupVO {
	private Long id;        //id
	private String name;    //规格组名称
	private Long cid;		//规格组品类id
	private String cname;	//品类名称
}
