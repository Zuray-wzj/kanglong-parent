package com.it18zhang.kanglong.service.api;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.entity.Sku;
import com.it18zhang.kanglong.common.entity.Spu;
import com.it18zhang.kanglong.common.entity.SpuDetail;
import com.it18zhang.kanglong.common.vo.PageResult;
import com.it18zhang.kanglong.common.vo.SpuVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SpuServiceAPI
 */
@FeignClient("business-service")
public interface SpuServiceApi {

	/**
	 * 按照id查询品牌
	 */
	@GetMapping("/spu/findspus")
	List<SpuVO> findSpus() ;

	/**
	 * 保存spu
	 */
	@PostMapping("/spu/saveOrUpdate")
	public void saveOrUpdate(@RequestBody Spu spu) ;

	/**
	 * 按照id删除spu
	 */
	@GetMapping("/spu/deletebyid")
	void deleteById(@RequestParam("spuid") Long id) ;

	/**
	 * 按照id查询Spu
	 */
	@GetMapping("/spu/findbyid")
	Spu findById(@RequestParam("id") Long id);



	@GetMapping("/spu/findskubyskuid")
	Sku findSkuBySkuId(@RequestParam("skuId")Long skuId);

}
