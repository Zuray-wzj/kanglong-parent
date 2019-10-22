package com.it18zhang.kanglong.service;

import com.it18zhang.kanglong.common.entity.SpecParam;
import com.it18zhang.kanglong.common.vo.SpecParamVO;
import com.it18zhang.kanglong.mapper.SpecParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 规格参数服务
 */
@Service
public class SpecParamService {

    @Autowired
    private SpecParamMapper specParamMapper  ;

    /**
     * 按照品类查询规格参数
     */
    public List<SpecParamVO> findByCid(Long cid){
        return specParamMapper.findByCid(cid) ;
    }

    /**
     * 保存更新规格参数
     */
    @Transactional
    public void saveOrUpdate(SpecParam param){
        if(param.getId() == null){
            specParamMapper.insert(param) ;
        }
        else{
            specParamMapper.updateByPrimaryKey(param) ;
        }
    }

    /**
     * 按照id查询规格参数
     */
    public SpecParam findById(Long id){
        return specParamMapper.selectByPrimaryKey(id) ;
    }

    /**
     * 按照id删除规格参数
     */
    public void deleteById(Long id){
        specParamMapper.deleteByPrimaryKey(id) ;
    }
}
