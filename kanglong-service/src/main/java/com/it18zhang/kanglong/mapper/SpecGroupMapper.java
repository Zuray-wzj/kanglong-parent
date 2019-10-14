package com.it18zhang.kanglong.mapper;

import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.entity.SpecGroup;
import com.it18zhang.kanglong.common.entity.SpecParam;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.common.vo.SpecGroupVO;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 规格组
 */
public interface SpecGroupMapper extends Mapper<SpecGroup> , IdListMapper<SpecGroup,Long>{
	//查询品类的规格组
	@Select("select * from t_spec_group where cid = #{cid}")
	public List<SpecGroup> findByCid(@Param("cid") Long cid);

	//查询规格组列表，按照品类排序
	@Select("select a.id id , a.name name,b.id cid, b.name cname from t_spec_group a , t_category b where a.cid = b.id order by a.cid , a.id")
	public List<SpecGroupVO> findGroups() ;

	//按照gid查询规格组
	@Select("select * from t_spec_group where id = #{id}")
	public SpecGroup findById(@Param("id") Long id) ;


	//按照品类id查询规格组以及参数
	@Select("select * from t_spec_group where cid = #{cid}")
	@Results({
			@Result(property = "id" , column = "id") ,
			@Result(property = "cid" , column = "cid") ,
			@Result(property = "name" , column = "name") ,
			@Result(property = "params" ,
					column = "id" ,
					many = @Many(select = "com.it18zhang.kanglong.mapper.SpecGroupMapper.findParamsByGid"))
	})
	public List<SpecGroup> findGroupAndParams(@Param("cid") Long cid) ;

	//按照规格组id查询规格参数
	@Select("select * from t_spec_param where group_id = #{gid}")
	public List<SpecParam> findParamsByGid(@Param("gid") Long gid);
}
