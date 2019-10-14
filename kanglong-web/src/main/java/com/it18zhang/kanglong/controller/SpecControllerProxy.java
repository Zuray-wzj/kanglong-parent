package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.entity.SpecGroup;
import com.it18zhang.kanglong.common.entity.SpecParam;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.common.vo.SpecGroupVO;
import com.it18zhang.kanglong.common.vo.SpecParamVO;
import com.it18zhang.kanglong.service.api.CategoryServiceApi;
import com.it18zhang.kanglong.service.api.SpecGroupServiceApi;
import com.it18zhang.kanglong.service.api.SpecParamServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SpecControllerProxy
 */
@Controller
@RequestMapping("/spec")
public class SpecControllerProxy {

	@Autowired
	private CategoryServiceApi categoryServiceApi ;

	@Autowired
	private SpecGroupServiceApi specGroupServiceApi ;

	@Autowired
	private SpecParamServiceApi specParamServiceApi ;

	/**
	 * 进入管理页面
	 */
	@GetMapping("/index")
	public String manage(Model model){
		//查询指定分类的子分类集合
		List<SpecGroupVO> list = specGroupServiceApi.findGroups();
		model.addAttribute("list" , list) ;

		List<CategoryVO> categories = categoryServiceApi.findTree().getBody() ;
		model.addAttribute("categories" , categories) ;

		return "/spec/groupManage" ;
	}

	//按照品类id查询规格组
	@GetMapping("/findbycid")
	@ResponseBody
	public List<SpecGroup> findByCid(@RequestParam("cid") Long cid){
		return specGroupServiceApi.findByCid(cid) ;
	}

	//保存或更新规格组
	@PostMapping("/saveOrUpdateGroup")
	@ResponseBody()
	public void saveOrUpdateGroup(SpecGroup group){
		specGroupServiceApi.saveOrUpdate(group);
	}


	//按照规格组id查询规格组
	@GetMapping("/findbygid")
	@ResponseBody()
	public SpecGroup findByGid(@RequestParam("gid") Long gid){
		return specGroupServiceApi.findById(gid);
	}

	//按照gid删除规格组
	@GetMapping("/deletegroupbygid")
	@ResponseBody
	public void deleteGroupByGid(@RequestParam("gid") Long gid){
		specGroupServiceApi.deleteById(gid);
	}

	/**
	 * 规格参数管理
	 */
	@GetMapping("/paramIndex")
	public String paramManage(Model model , @RequestParam("cid") Long cid){
		List<SpecParamVO> params = specParamServiceApi.findByCid(cid) ;
		model.addAttribute("params" , params) ;
		return "spec/paramManage" ;
	}

	/**
	 * 保存更新规格参数
	 */
	@PostMapping("/saveOrUpdateParam")
	@ResponseBody
	public void saveOrUpdateParam(SpecParam param){
		specParamServiceApi.saveOrUpdate(param);
	}

	/**
	 * 按照规格参数id查询规格对象
	 */
	@GetMapping("/findparambypid")
	@ResponseBody
	public SpecParam findByPid(@RequestParam("pid") Long pid){
		return specParamServiceApi.findById(pid) ;
	}

	/**
	 * 按照id删除规格参数
	 */
	@GetMapping("/deleteparambypid")
	@ResponseBody
	public void deleteParamByPid(Long pid){
		specParamServiceApi.deleteById(pid);
	}

	@GetMapping("/findgroupandparamsbycid")
	@ResponseBody
	public List<SpecGroup> findGroupAndParamsByCid(@RequestParam("cid") Long cid){
		return specGroupServiceApi.findGroupAndParamsByCid(cid) ;
	}
}
