package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.service.api.CategoryServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CategoryControllerProxy
 */
@Controller
@RequestMapping("/category")
public class CategoryControllerProxy {

	//注入categoryService
	@Autowired
	private CategoryServiceApi categoryServiceApi;

	/**
	 * 品牌管理使用该方法
	 */
	@RequestMapping("/findall")
	@ResponseBody
	public List<Category> findall(){
		//远程查询所有的品牌
		List<Category> all = categoryServiceApi.findAll().getBody() ;
		return all ;
	}


	/**
	 * 品牌管理使用该方法
	 */
	@RequestMapping("/findbybid")
	@ResponseBody
	public List<Category> findByBrandId(@RequestParam("bid") Long bid){
		//远程查询所有的品牌
		List<Category> list = categoryServiceApi.findByBrandId(bid).getBody() ;
		return list ;
	}

	/**
	 * 品类管理的入口点
	 */
	@RequestMapping("/index")
	public String index(Model model){
		//查询树形品类集合
		List<CategoryVO> list = categoryServiceApi.findTree().getBody() ;
		model.addAttribute("list" , list) ;
		return "/category/manage" ;
	}

	//保存或更新
	@PostMapping("/saveOrUpdate")
	@ResponseBody
	public void saveOrUpdate(Category category) {
		categoryServiceApi.saveOrUpdate(category);
	}

	//按照id删除品类
	@GetMapping("/deletebyid")
	@ResponseBody
	public void deleteById(@RequestParam("id") Long id){
		categoryServiceApi.deleteById(id);
	}

	//按照id查询品类
	@GetMapping("/findbyid")
	@ResponseBody
	public Category findById(@RequestParam("id") Long id){
		return categoryServiceApi.findById(id) ;
	}

	//按照id查询品类
	@GetMapping("/findsublist")
	@ResponseBody
	public List<Category> findSubList(@RequestParam("pid") Long pid){
		return categoryServiceApi.findSubList(pid) ;
	}

	//查询树形品类列表
	@GetMapping("/findtree")
	@ResponseBody
	public List<CategoryVO> findTree(){
		return categoryServiceApi.findTree().getBody() ;
	}
}
