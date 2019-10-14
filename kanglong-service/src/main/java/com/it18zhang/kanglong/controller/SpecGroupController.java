package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.entity.SpecGroup;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.common.vo.SpecGroupVO;
import com.it18zhang.kanglong.service.CategoryService;
import com.it18zhang.kanglong.service.SpecGroupService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SpecGroupController
 */
@RestController
@RequestMapping("/specgroup")
public class SpecGroupController {
	@Autowired
	private SpecGroupService specGroupService ;

	//按照品类id查询规格组
	@GetMapping("/findbycid")
	public List<SpecGroup> findByCid(@RequestParam("cid") Long cid){
		return specGroupService.findByCid(cid) ;
	}

	//按照品类id查询规格组
	@GetMapping("/findbyid")
	public SpecGroup findById(@RequestParam("id") Long id){
		return specGroupService.findById(id) ;
	}

	//查询所有规格组，按照品类排序
	@GetMapping("/findgroups")
	public List<SpecGroupVO> findGroups(){
		return specGroupService.findGroups() ;
	}

	//查询所有规格组，按照品类排序
	@PostMapping("/saveOrUpdate")
	public void saveOrUpdate(@RequestBody SpecGroup group){
		specGroupService.saveOrUpdate(group);
	}

	//按照id删除规格组
	@GetMapping("/deletebyid")
	public void deleteById(@RequestParam("id") Long id){
		specGroupService.deleteById(id);
	}

	//按照id删除规格组
	@GetMapping("/findgroupandparamsbycid")
	public List<SpecGroup> findGroupAndParamsByCid(@RequestParam("cid") Long cid){
		return specGroupService.findGroupAndParamsByCid(cid) ;
	}
}
