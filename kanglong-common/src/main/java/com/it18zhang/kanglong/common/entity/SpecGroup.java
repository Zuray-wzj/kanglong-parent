package com.it18zhang.kanglong.common.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * 规格组
 */
@Data
@Table(name="t_spec_group")
public class SpecGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                //组id
    private Long cid;               //属于哪个类别id
    private String name;            //组名
    private List<SpecParam> params; //规格参数列表
}
