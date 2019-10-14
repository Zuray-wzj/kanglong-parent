package com.it18zhang.kanglong.mapper;

import com.it18zhang.kanglong.common.entity.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BrandMapper
 */
public interface BrandMapper extends Mapper<Brand> , IdListMapper<Brand,Long>{
    /**
     * 相中见表插入数据
     */
    @Insert("insert into t_category_brand(category_id , brand_id) values(#{cid} , #{bid})")
    int insertLink(@Param("cid") Long cid , @Param("bid") Long bid);

    //按照品类id查询品牌
    @Select("select b.* from t_category_brand a right outer join t_brand b on a.brand_id = b.id where a.category_id = #{cid}")
    List<Brand> findByCid(@Param("cid") Long cid);

    //删除关联关系
    @Delete("delete from t_category_brand where brand_id = #{bid}")
    void deleteLink(@Param("bid") Long bid);
}
