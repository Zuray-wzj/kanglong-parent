package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.*;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.common.vo.SpecGroupVO;
import com.it18zhang.kanglong.common.vo.SpecParamVO;
import com.it18zhang.kanglong.common.vo.SpuVO;
import com.it18zhang.kanglong.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * SpuControllerProxy
 */
@Controller
@RequestMapping("/spu")
public class SpuControllerProxy {

	@Autowired
	private SpuServiceApi spuServiceApi;

	@Autowired
	private CategoryServiceApi categoryServiceApi ;

	@Autowired
	private BrandServiceApi brandServiceApi ;

	/**
	 * 商品管理页面入口
	 */
	@GetMapping("/index")
	public String manage(Model model){
		//查询所有品类
		List<CategoryVO> categories = categoryServiceApi.findTree().getBody() ;
		List<SpuVO> spus = spuServiceApi.findSpus() ;
		model.addAttribute("spus" , spus) ;
		model.addAttribute("categories" , categories) ;
		return "/spu/manage" ;
	}

	/**
	 * 保存或更新Spu
	 */
	@PostMapping("/saveOrUpdate")
	@ResponseBody
	public void saveOrUpdate(Spu spu , SpuDetail spuDetail , HttpServletRequest request){
		//取得所有参数,组装成sku的集合
		Map<String, String[]> map = request.getParameterMap() ;
		//
		String[] skus = request.getParameterValues("sku") ;
		List<Sku> list = new ArrayList<Sku>() ;
		Sku sku = null ;
		for(String s : skus){
			sku = new Sku() ;
			Date date = new Date() ;
			if(sku.getId() == null){
				sku.setCreateTime(date);
				sku.setLastUpdateTime(date);
			}
			else{
				sku.setLastUpdateTime(date);
			}
			//是否启用
			sku.setEnable(getBoolean(map , s + ".enable"));
			sku.setId(getLong(map, s + ".id"));
			sku.setPrice(getLong(map, s + ".price"));
			sku.setTitle(getString(map, s + ".title"));
			sku.setOwnSpec(s);
			sku.setStock(getInteger(map , s + ".stock"));
			list.add(sku) ;
		}

		//组装spu对象
		spu.setSpuDetail(spuDetail);
		spu.setSkus(list);
		spuServiceApi.saveOrUpdate(spu);
	}

	/**
	 * 从map提取String值
	 */
	private String getString(Map<String,String[]> map , String key){
		String[] values = map.get(key) ;
		if(values != null && values.length > 0){
			String str = values[0] ;
			if(str != null && !str.trim().equals("")){
				return str.trim() ;
			}
		}
		return null ;
	}

	/**
	 * 从map提取boolean值
	 */
	private Boolean getBoolean(Map<String,String[]> map , String key){
		String[] values = map.get(key) ;
		if(values != null && values.length > 0){
			String str = values[0] ;
			if(str != null && !str.trim().equals("")){
				return Boolean.parseBoolean(str) ;
			}
		}
		return null ;
	}
	/**
	 * 从map提取Integer值
	 */
	private Integer getInteger(Map<String,String[]> map , String key){
		String[] values = map.get(key) ;
		if(values != null && values.length > 0){
			String str = values[0] ;
			if(str != null && !str.trim().equals("")){
				return Integer.parseInt(str);
			}
		}
		return null ;
	}
	/**
	 * 从map提取Long值
	 */
	private Long getLong(Map<String,String[]> map , String key){
		String[] values = map.get(key) ;
		if(values != null && values.length > 0){
			String str = values[0] ;
			if(str != null && !str.trim().equals("")){
				return Long.parseLong(str) ;
			}
		}
		return null ;
	}

	/**
	 * 按照id删除spu
	 */
	@GetMapping("/deletebyid")
	@ResponseBody
	public void deleteById(@RequestParam("spuid") Long spuid){
		spuServiceApi.deleteById(spuid);
	}

	/**
	 * 按照id查询商品
	 */
	@GetMapping("/findbyid")
	@ResponseBody
	public Spu findById(@RequestParam("id") Long id){
		return spuServiceApi.findById(id) ;
	}
}
