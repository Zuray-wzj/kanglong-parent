package com.it18zhang.kanglong.mapper;

import com.it18zhang.kanglong.common.entity.SpecGroup;
import com.it18zhang.kanglong.common.entity.SpecParam;
import com.it18zhang.kanglong.common.vo.SpecParamVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 规格参数
 */
public interface SpecParamMapper extends Mapper<SpecParam> , IdListMapper<SpecParam,Long>{
	@Select("select * from t_spec_param where group_id = #{gid}")
	public List<SpecParam> findByGid(@Param("gid") Long gid);

	//按照品类查询规格参数
	@Select("select a.id id , a.cid cid , a.generic generic , a.group_id groupId , a.`name` name , a.`numeric` `numeric` , a.searching searching , a.segments segments , a.unit unit , b.`name` cname , c.`name` groupName  from t_spec_param a , t_category b , t_spec_group c where a.cid = b.id and a.group_id = c.id and a.cid = #{cid} order by a.group_id")
	public List<SpecParamVO> findByCid(@Param("cid") Long cid) ;
}
