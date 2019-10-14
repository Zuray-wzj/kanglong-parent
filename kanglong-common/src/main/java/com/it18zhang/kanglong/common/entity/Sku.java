package com.it18zhang.kanglong.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Stock keeping unit,库存量单位
 */
@Data
@Table(name="t_sku")
public class Sku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long spuId;         //spuid
    private String title;       //标题
    private String images;      //图片
    private Long price;         //价格
    private String ownSpec;     //自身特殊的规格
    private String indexes;     //商品特殊规格的下标
    private Boolean enable;     //是否有效，逻辑删除用
    private Date createTime;    //创建时间
    private Date lastUpdateTime;//最后修改时间

    @Transient
    private Integer stock;      //库存量
}
