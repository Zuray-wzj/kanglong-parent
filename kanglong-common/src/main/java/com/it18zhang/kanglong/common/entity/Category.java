package com.it18zhang.kanglong.common.entity;

import lombok.Data;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Column;

/**
 * 品类，类别
 */
@Data
@Table(name = "t_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        //id
    private String name;    //类名
    @Column(name = "parent_id")
    private Long parentId = 0L;  //上级品类id
    //修改成Boolean，否则mybatis无法通过get方式提取到。
    private Boolean leaf = false;   //是否是最后一别品类
    private Integer idx = 1;    //排序值
}
