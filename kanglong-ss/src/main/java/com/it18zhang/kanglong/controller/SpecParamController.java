package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.SpecParam;
import com.it18zhang.kanglong.common.vo.SpecParamVO;
import com.it18zhang.kanglong.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SpecParamController
 */
@RestController
@RequestMapping("/specparam")
public class SpecParamController {

    @Autowired
    private SpecParamService specParamService ;

    /**
     * 按照品类查询规格参数
     */
    @GetMapping("/findbycid")
    public List<SpecParamVO> findByCid(@RequestParam("cid") Long cid){
        return specParamService.findByCid(cid) ;
    }

    /**
     * 保存更新规格参数
     */
    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody SpecParam param){
        specParamService.saveOrUpdate(param);
    }

    @GetMapping("/findbyid")
    public SpecParam findById(@RequestParam("id") Long id){
        return specParamService.findById(id) ;
    }

    /**
     * 按照id删除规格参数
     */
    @GetMapping("/deletebyid")
    public void deleteById(@RequestParam("id") Long id){
        specParamService.deleteById(id);
    }
}
