package com.it18zhang.kanglong.common.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * 品牌类
 */
@Data
@Table(name="t_brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                //id
    private String name ;           //品牌名称
    private String image;           //图像，logo
    @Column(name = "firstletter")
    private Character firstLetter;  //品牌首字母
}
