package com.it18zhang.kanglong.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * standard product unit,标准产品单位,商品
 */
@Data
@Table(name="t_spu")
public class Spu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //
    @Column(name = "brand_id")
    private Long brandId ;      //属于的品牌id
    private Long cid1;          //1级类目id
    private Long cid2;          //2级类目id
    private Long cid3;          //3级类目id
    private String title;       //标题
    private String subTitle;    //子标题
    private Boolean saleable = true;    //是否可出售
    private Boolean valid = true;      //是否有效，逻辑删除用
    private Date createTime;    //创建时间
    private Date lastUpdateTime;//最后修改时间
    @Transient
    private SpuDetail spuDetail ;   //明细
    @Transient
    private List<Sku> skus ;        //sku列表
}
