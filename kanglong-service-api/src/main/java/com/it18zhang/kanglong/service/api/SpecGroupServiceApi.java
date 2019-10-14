package com.it18zhang.kanglong.service.api;

import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.entity.SpecGroup;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.common.vo.SpecGroupVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * SpecGroupServiceApi
 */
@FeignClient("business-service")
public interface SpecGroupServiceApi {

	//按照品类id查询规格组
	@GetMapping("/specgroup/findbycid")
	public List<SpecGroup> findByCid(@RequestParam("cid") Long cid);

	//按照规格组id查询规格组
	@GetMapping("/specgroup/findbyid")
	public SpecGroup findById(@RequestParam("id") Long id);

	//按照品类id查询规格组
	@GetMapping("/specgroup/findgroups")
	public List<SpecGroupVO> findGroups();

	//保存或更新规格组
	@PostMapping("/specgroup/saveOrUpdate")
	public void saveOrUpdate(@RequestBody SpecGroup group);

	//按照id删除规格组
	@GetMapping("/specgroup/deletebyid")
	public void deleteById(@RequestParam("id") Long id) ;

	//按照品类id查询规格组和参数
	@GetMapping("/specgroup/findgroupandparamsbycid")
	public List<SpecGroup> findGroupAndParamsByCid(@RequestParam("cid") Long cid) ;
}
