package com.it18zhang.kanglong.service.api;

import com.it18zhang.kanglong.common.entity.SpecParam;
import com.it18zhang.kanglong.common.vo.SpecParamVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * SpecParamServiceApi
 */
@FeignClient("business-service")
public interface SpecParamServiceApi {
    //按照品类查询规格参数
    @GetMapping("/specparam/findbycid")
    public List<SpecParamVO> findByCid(@RequestParam("cid") Long cid) ;

    //按照id查询规格参数
    @GetMapping("/specparam/findbyid")
    public SpecParam findById(@RequestParam("id") Long id) ;

    @PostMapping("/specparam/saveOrUpdate")
    public void saveOrUpdate(@RequestBody SpecParam param) ;

    //按照id删除规格参数
    @GetMapping("/specparam/deletebyid")
    public void deleteById(@RequestParam("id") Long id) ;


}
