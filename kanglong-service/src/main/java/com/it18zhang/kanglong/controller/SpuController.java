package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.entity.Spu;
import com.it18zhang.kanglong.common.entity.SpuDetail;
import com.it18zhang.kanglong.common.vo.PageResult;
import com.it18zhang.kanglong.common.vo.SpuVO;
import com.it18zhang.kanglong.service.BrandService;
import com.it18zhang.kanglong.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * SpuController
 */
@RestController
@RequestMapping("/spu")
public class SpuController {
	@Autowired
	private SpuService spuService;

	//按照id查询品牌
	@GetMapping("/findspus")
	public List<SpuVO> findSpus(){
		return spuService.findSpus() ;
	}

	@PostMapping("/saveOrUpdate")
	public void saveOrUpdate(@RequestBody Spu spu){
		spuService.saveOrUpdate(spu) ;
		System.out.println("kk");
	}

	/**
	 * 按照id删除spu
	 */
	@GetMapping("/deletebyid")
	public void deleteById(@RequestParam("spuid") Long id){
		spuService.deleteById(id);
	}

	/**
	 * 按照id查询商品
	 */
	@GetMapping("/findbyid")
	public Spu findById(@RequestParam("id") Long id){
		return spuService.findById(id) ;
	}
}
