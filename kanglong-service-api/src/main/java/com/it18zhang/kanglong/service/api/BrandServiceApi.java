package com.it18zhang.kanglong.service.api;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.vo.PageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * BrandService
 */
@FeignClient("business-service")
public interface BrandServiceApi {

	/**
	 * 按照id查询品牌
	 */
	@GetMapping("/brand/findbyid")
	ResponseEntity<Brand> findById(@RequestParam(value="id") Long id) ;

	/**
	 * 分页查询，指定各种条件
	 */
	@GetMapping("/brand/paging")
	public ResponseEntity<PageResult<Brand>> findPagingAndSortByName(
			@RequestParam(value = "pno", defaultValue = "1") Integer pno,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@RequestParam(value = "desc", defaultValue = "false") Boolean desc,
			@RequestParam(value = "name", required = false) String name) ;

	//按照品类查询品牌
	@GetMapping("/brand/findbycid")
	public ResponseEntity<List<Brand>> findByCid(@RequestParam("cid") Long cid) ;

	//按照id集合查询品牌
	@GetMapping("/brand/findbyids")
	public ResponseEntity<List<Brand>> findByIds(@RequestParam("ids") Long[] ids) ;

	//保存或更新品牌
	@PostMapping("/brand/saveOrUpdate")
	public ResponseEntity<String> saveOrUpdate(@RequestBody Brand brand , @RequestParam("cids") Long[] cids) ;

	//删除品牌
	@GetMapping("/brand/deletebyid")
	public void deleteById(@RequestParam("id") Long id) ;

	//查询所有品牌
	@GetMapping("/brand/findall")
	public List<Brand> findAll() ;
}
