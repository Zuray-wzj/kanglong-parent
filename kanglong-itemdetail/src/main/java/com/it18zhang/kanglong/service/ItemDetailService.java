package com.it18zhang.kanglong.service;

import com.it18zhang.kanglong.service.api.*;
import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.entity.SpecGroup;
import com.it18zhang.kanglong.common.entity.Spu;
import com.it18zhang.kanglong.common.vo.SpecParamVO;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情服务
 */
@Service
public class ItemDetailService {

	@Autowired
	private SpuServiceApi spuServiceApi ;

	@Autowired
	private BrandServiceApi brandServiceApi ;

	@Autowired
	private CategoryServiceApi categoryServiceApi ;

	@Autowired
	private SpecGroupServiceApi specGroupServiceApi ;

	@Autowired
	private SpecParamServiceApi specParamServiceApi ;


	public Map<String,Object> loadModel(Long id) {
		Map<String,Object> map = new HashMap<String,Object>() ;
		// 1.查询spu，包括spuDetail、skus
		Spu spu = spuServiceApi.findById(id) ;
		map.put("spu",spu) ;
		map.put("spuDetail",spu.getSpuDetail()) ;
		map.put("skus",spu.getSkus()) ;

		// 2.查询品牌
		Long brandId = spu.getBrandId() ;
		Brand brand = brandServiceApi.findById(brandId).getBody() ;
		map.put("brand",brand) ;

		// 3.查询品类
		List<Category> categories = categoryServiceApi.findByIds(new Long[]{spu.getCid1(),spu.getCid2(),spu.getCid3()}) ;
		map.put("categories" , categories)  ;

		// 4.查询规格组
		List<SpecGroup> specGroups = specGroupServiceApi.findByCid(spu.getCid3()) ;
		map.put("groups" , specGroups)  ;

		//查询所有的规格参数
		List<SpecParamVO> specParamVOS = specParamServiceApi.findByCid(spu.getCid3()) ;
		Map<Long,String> specParamMap = new HashMap<Long,String>() ;
		for(SpecParamVO vo : specParamVOS){
			specParamMap.put(vo.getId() , vo.getName()) ;
		}
		map.put("paramMap", specParamMap);
		return map ;
	}
}
