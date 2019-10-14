package com.it18zhang.kanglong.service;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.exception.ExceptionMessage;
import com.it18zhang.kanglong.common.exception.KangLongException;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.mapper.BrandMapper;
import com.it18zhang.kanglong.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 品类服务
 */
@Service
public class CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private BrandMapper brandMapper;

	/**
	 * 查询所有品类
	 */
	public List<Category> findAll(){
		return categoryMapper.findAll() ;
	}

	/**
	 * 查询品牌id所属的品类
	 */
	public List<Category> findByBrandId(Long bid){
		return categoryMapper.findByBrandId(bid) ;
	}

	/**
	 * 按照树形层级结构查询
	 */
	public List<CategoryVO> findTree(){
		return categoryMapper.findTree() ;
	}

	/**
	 * 保存或更新
	 */
	@Transactional
	public void saveOrUpdate(Category category){
		//插入
		if(category.getId() == null){
			categoryMapper.insert(category) ;
		}
		else{
			categoryMapper.updateByPrimaryKey(category) ;
		}
	}

	/**
	 * 删除品类，不能有孩子，不能有品牌与之关联
	 */
	@Transactional
	public void deleteById(Long id){
		//查询子品类的数量
		int cnt = categoryMapper.findSubCount(id) ;
		//有孩子,不能删除
		if(cnt != 0 ){
			throw new KangLongException(ExceptionMessage.CATEGORY_DELETE_ERROR) ;
		}
		else{
			//查询是否有关联的品牌
			List<Brand> brand = brandMapper.findByCid(id) ;
			if(!CollectionUtils.isEmpty(brand)){
				throw new KangLongException(ExceptionMessage.CATEGORY_DELETE_ERROR);
			}
		}
		//执行删除
		categoryMapper.deleteByPrimaryKey(id) ;
	}

	/**
	 * 按照id查询品类
	 */
	public Category findById(Long id){
		Category c =  categoryMapper.selectByPrimaryKey(id) ;
		if(c == null){
			throw new KangLongException(ExceptionMessage.CATEGORY_NOT_FOUND_ERROR) ;
		}
		return c ;
	}

	/**
	 * 查询品类的子品类
	 */
	public List<Category> findSubList(Long pid){
		return categoryMapper.findSubList(pid) ;
	}
}
