package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.vo.PageResult;
import com.it18zhang.kanglong.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * BrandController
 */
@RestController
@RequestMapping("/brand")
public class BrandController {
	@Autowired
	private BrandService brandService ;

	//按照id查询品牌
	@GetMapping("/findbyid")
	public ResponseEntity<Brand> findById(@RequestParam(value = "id") Long id){
		return ResponseEntity.ok(brandService.findById(id)) ;
	}

	/**
	 * 按照名称或首字母分页查询品牌并排序
	 */
	@GetMapping("/paging")
	public ResponseEntity<PageResult<Brand>> findPagingAndSortByName(
			@RequestParam(value = "pno", defaultValue = "1") Integer pno,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@RequestParam(value = "desc", defaultValue = "false") Boolean desc,
			@RequestParam(value = "name", required = false) String name){
		PageResult<Brand> result = brandService.findPagingAndSort(pno, rows, sortBy, desc, name);
		if (result == null || result.getList().size() == 0) {
			return new ResponseEntity<PageResult<Brand>>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(result);
	}

	//按照品类id查询所有品牌
	@GetMapping("/findbycid")
	public ResponseEntity<List<Brand>> findByCid(@RequestParam("cid") Long cid){
		return ResponseEntity.ok(brandService.findByCid(cid)) ;
	}

	//按照品牌id集合品牌
	@GetMapping("/findbyids")
	public ResponseEntity<List<Brand>> findByIds(@RequestParam("ids") Long[] ids){
		List<Long> ids0 = Arrays.asList(ids) ;
		return ResponseEntity.ok(brandService.findByIds(ids0)) ;
	}

	//按照品牌id集合品牌
	@PostMapping("/saveOrUpdate")
	public ResponseEntity<String> saveOrUpdate(@RequestBody Brand brand , @RequestParam("cids") Long[] cids){
		List<Long> cids0 = Arrays.asList(cids);
		brandService.saveOrUpdateBrand(brand , cids0);
		return ResponseEntity.ok("Ok") ;
	}

	/**
	 * 按照id删除品牌
	 */
	@GetMapping("/deletebyid")
	@ResponseBody
	public void deleteById(@RequestParam("id") Long id){
		brandService.deleteById(id) ;
	}

	@GetMapping("/findall")
	public List<Brand> findAll(){
		return brandService.findAll() ;
	}
}
