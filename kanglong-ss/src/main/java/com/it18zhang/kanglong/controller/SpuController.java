package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.Sku;
import com.it18zhang.kanglong.common.entity.Spu;
import com.it18zhang.kanglong.common.vo.SpuVO;
import com.it18zhang.kanglong.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

	//按照id查询品牌
	@GetMapping("/yyy")
	public void findSpu33s1()
	{
		Spu spu=spuService.findById(2L);
		spuService.saveOrUpdate(spu);
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

	/**
	 * 按照id查询商品
	 */
	@GetMapping("/findskubyskuid")
	public Sku findSkuBySkuId(@RequestParam("skuId") Long skuId){
		return spuService.findSkuBySkuId(skuId) ;
	}
}
