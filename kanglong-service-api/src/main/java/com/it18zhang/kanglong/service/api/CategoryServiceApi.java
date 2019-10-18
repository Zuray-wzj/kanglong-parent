package com.it18zhang.kanglong.service.api;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.common.vo.PageResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * CategoryServiceApi
 */
@FeignClient("business-service")
public interface CategoryServiceApi {

	/**
	 * 查询所有品类
	 */
	@GetMapping("/category/findall")
	ResponseEntity<List<Category>> findAll() ;

	/**
	 * 查询指定品牌所属品类
	 */
	@GetMapping("/category/findbybid")
	ResponseEntity<List<Category>> findByBrandId(@RequestParam("bid") Long bid) ;

	//查询树形品类集合
	@GetMapping("/category/findtree")
	ResponseEntity<List<CategoryVO>> findTree();

	//保存或更新
	@PostMapping("/category/saveOrUpdate")
	void saveOrUpdate(@RequestBody Category category) ;

	//按照id删除品类
	@GetMapping("/category/deletebyid")
	public void deleteById(@RequestParam("id") Long id);

	//按照id查询
	@GetMapping("/category/findbyid")
	public Category findById(@RequestParam("id") Long id ) ;

	//查询子品类列表
	@GetMapping("/category/findsublist")
	public List<Category> findSubList(@RequestParam("pid") Long pid);

	//按照id集合查询Category
	@GetMapping("/category/findbyids")
	public List<Category> findByIds(@RequestParam("ids") Long[] ids) ;
}
