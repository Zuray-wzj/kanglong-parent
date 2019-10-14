package com.it18zhang.kanglong.service;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.entity.SpecGroup;
import com.it18zhang.kanglong.common.entity.SpecParam;
import com.it18zhang.kanglong.common.exception.ExceptionMessage;
import com.it18zhang.kanglong.common.exception.KangLongException;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.common.vo.SpecGroupVO;
import com.it18zhang.kanglong.mapper.BrandMapper;
import com.it18zhang.kanglong.mapper.CategoryMapper;
import com.it18zhang.kanglong.mapper.SpecGroupMapper;
import com.it18zhang.kanglong.mapper.SpecParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 规格组服务
 */
@Service
public class SpecGroupService {
	@Autowired
	private SpecGroupMapper specGroupMapper ;
	//参数mapper
	@Autowired
	private SpecParamMapper specParamMapper ;

	/**
	 * 按照品类查询规格组
	 */
	public List<SpecGroup> findByCid(Long cid) {
		List<SpecGroup> list = specGroupMapper.findByCid(cid) ;
		if(!CollectionUtils.isEmpty(list)){
			for(SpecGroup sg : list){
				List<SpecParam> params = specParamMapper.findByGid(sg.getId()) ;
				sg.setParams(params);
			}
		}
		return list ;
	}

	/**
	 * 查询规格组,按照品类排序
	 */
	public List<SpecGroupVO> findGroups() {
		return specGroupMapper.findGroups() ;
	}

	/**
	 * 插入规格组
	 */
	public void saveOrUpdate(SpecGroup group){
		if(group.getId() == null){
			specGroupMapper.insert(group) ;
		}
		else{
			specGroupMapper.updateByPrimaryKey(group) ;
		}
	}

	/**
	 * 按照id查询
	 * @param id
	 * @return
	 */
	public SpecGroup findById(Long id){
		return specGroupMapper.findById(id) ;
	}

	/**
	 * 按照id删除规格组
	 */
	public void deleteById(Long id){
		List<SpecParam> params = specParamMapper.findByGid(id) ;
		if(CollectionUtils.isEmpty(params)){
			specGroupMapper.deleteByPrimaryKey(id) ;
		}
		else{
			throw new KangLongException("改规格组非空，不允许删除！") ;
		}
	}

	/**
	 * 按照品类id查询规格组和参数
	 */
	public List<SpecGroup> findGroupAndParamsByCid(Long cid){
		return specGroupMapper.findGroupAndParams(cid) ;
	}

}
