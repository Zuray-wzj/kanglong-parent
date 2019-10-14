package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.common.vo.PageResult;
import com.it18zhang.kanglong.service.BrandService;
import com.it18zhang.kanglong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * CategoryController
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	//按照id查询品牌
	@GetMapping("/findall")
	public ResponseEntity<List<Category>> findAll(){
		return  ResponseEntity.ok(categoryService.findAll()) ;
	}


	//按照品牌id查询品类
	@GetMapping("/findbybid")
	public ResponseEntity<List<Category>> findByBrandId(@RequestParam("bid") Long bid){
		return  ResponseEntity.ok(categoryService.findByBrandId(bid)) ;
	}

	//按照品牌id查询品类
	@GetMapping("/findtree")
	public ResponseEntity<List<CategoryVO>> findTree(){
		return  ResponseEntity.ok(categoryService.findTree()) ;
	}

	//按照品牌id查询品类
	@PostMapping("/saveOrUpdate")
	public void saveOrUpdate(@RequestBody Category category){
		categoryService.saveOrUpdate(category) ;
	}

	//按照品牌id查询品类
	@GetMapping("/deletebyid")
	public void deleteById(@RequestParam("id") Long id){
		categoryService.deleteById(id);
	}

	//按照品牌id查询品类
	@GetMapping("/findbyid")
	public Category findById(@RequestParam("id") Long id){
		return categoryService.findById(id);
	}

	/**
	 * 查询子品类的列表
	 */
	@GetMapping("/findsublist")
	public List<Category> findSubList(@RequestParam("pid") Long pid){
		return categoryService.findSubList(pid) ;
	}
}
