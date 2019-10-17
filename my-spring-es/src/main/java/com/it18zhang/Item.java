package com.it18zhang;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Item
 */
@Document(indexName = "it18zhang" , type = "items" ,shards = 1 , replicas = 0)
@Data
public class Item {
	@Id
	private Long id ;

	@Field(type = FieldType.Text , analyzer = "ik_max_word")
	private String title ;

	@Field(type = FieldType.Keyword)
	private String category ;

	@Field(type = FieldType.Keyword)
	private String brand ;

	@Field(type = FieldType.Float)
	private Double price ;

	@Field(type = FieldType.Keyword , index = false)
	private String image ;
}
