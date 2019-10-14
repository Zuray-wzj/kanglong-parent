package com.it18zhang.kanglong.mapper;

import com.it18zhang.kanglong.common.entity.Sku;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * SkuMapper
 */
public interface SkuMapper extends Mapper<Sku> , IdListMapper<Sku,Long>{

	@Delete("delete from t_sku where spu_id = #{id}")
	void deleteBySpuId(@Param("id") Long id);
}
