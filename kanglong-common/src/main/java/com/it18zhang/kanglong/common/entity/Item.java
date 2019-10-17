package com.it18zhang.kanglong.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 在es中的商品type，主要用于搜索。
 */
@Document(indexName = "kanglong" , type = "items" , shards = 1 , replicas = 0)
@Data
public class Item {
    @Id
    private Long id ;

    //全部搜索内容
    @Field(type = FieldType.Text , analyzer = "ik_max_word")
    private String all ;

    //子标题
    @Field(type = FieldType.Keyword , index = false)
    private String subTitle ;

    @Field(type = FieldType.Long)
    private Long brandId ;

    @Field(type = FieldType.Long)
    private Long cid1 ;

    @Field(type = FieldType.Long)
    private Long cid2 ;

    @Field(type = FieldType.Long)
    private Long cid3 ;

    @Field(type = FieldType.Date)
    private Date createTime ;

    //采用自适应方式
    @Field(type = FieldType.Long)
    private List<Long> price ;

    @Field(type = FieldType.Keyword , index = false)
    private String skus ;

    //采用自适应方式
    @Field(type = FieldType.Object)
    private Map<String,Object> specs ;
}
