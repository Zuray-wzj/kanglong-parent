package com.it18zhang.kanglong.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.exception.ExceptionMessage;
import com.it18zhang.kanglong.common.exception.KangLongException;
import com.it18zhang.kanglong.common.vo.PageResult;
import com.it18zhang.kanglong.mapper.BrandMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.List;

/**
 * 品牌服务
 */
@Service
public class BrandService {
	@Autowired
	private BrandMapper brandMapper ;

	/**
	 * 按照id查询
	 */
	public Brand findById(Long id) {
		Brand brand = brandMapper.selectByPrimaryKey(id) ;
		if(brand == null){
			throw new KangLongException(ExceptionMessage.BRAND_NOT_FOUND) ;
		}
		return brand;
	}

	/**
	 * 分页查询并排序 , 按照name或者fistletter进行过滤
	 * pno		:页号
	 * rows		:每页记录数
	 * sortBy	:排序字段
	 * desc 	:是否降序
	 */
	public PageResult findPagingAndSort(Integer pno  , Integer rows , String sortBy , Boolean desc , String brandName){
		//设置起始页
		PageHelper.startPage(pno , rows) ;
		//
		Example example = new Example(Brand.class) ;
		//name有效,也可以作为首字母查询
		if(StringUtils.isNotBlank(brandName)){
			example.createCriteria().andLike("name" , "%" + brandName + "%" )
					.orEqualTo("firstLetter" , brandName) ;
		}
		//处理排序字段
		if(StringUtils.isNotBlank(sortBy)){
			String orderByClause = sortBy + (desc ? " desc" : " asc") ;
			example.setOrderByClause(orderByClause);
		}

		Page<Brand> page = (Page<Brand>) brandMapper.selectByExample(example);

		PageResult result = new PageResult() ;
		result.setCount(page.getTotal());
		result.setList(page);
		return result ;
	}

	/**
	 * 保存品牌，同时指定所属的品类集合
	 * 保证事务性
	 */
	@Transactional
	public void saveOrUpdateBrand(Brand brand , List<Long> cids){
		//执行插入
		if(brand.getId() == null){
			//执行插入 , id由数据库自动生成，自动回置给类。
			int cnt = brandMapper.insert(brand);
			if (cnt != 1) {
				throw new KangLongException(ExceptionMessage.BRAND_SAVE_ERROR);
			}
			//向中间表插入记录
			for (Long cid : cids) {
				cnt = brandMapper.insertLink(cid, brand.getId());
				if (cnt != 1) {
					throw new KangLongException(ExceptionMessage.CATEGORY_NOT_FOUND);
				}
			}
		}
		//update
		else{
			//按照主键更新
			brandMapper.updateByPrimaryKey(brand);
			//删除品类的关联
			brandMapper.deleteLink(brand.getId());
			//重新创建关联
			for (Long cid : cids) {
				brandMapper.insertLink(cid, brand.getId());
			}
		}
	}

	//按照品类查询品牌
	public List<Brand> findByCid(Long cid){
		List<Brand> list = brandMapper.findByCid(cid) ;
		if(CollectionUtils.isEmpty(list)){
			throw new KangLongException(ExceptionMessage.BRAND_NOT_FOUND) ;
		}
		return list ;
	}


	//按照ids集合查询品牌
	public List<Brand> findByIds(List<Long> ids){
		List<Brand> list = brandMapper.selectByIdList(ids) ;
		if(CollectionUtils.isEmpty(list)){
			throw new KangLongException(ExceptionMessage.BRAND_NOT_FOUND) ;
		}
		return list ;
	}
	/**
	 * 按照id删除品牌
	 */
	@Transactional
	public void deleteById(Long id){
		//删除图片
		Brand b = this.findById(id) ;
		if(b != null){
			//删除图片
			String logoPath = b.getImage() ;
			if(StringUtils.isNotEmpty(logoPath)){
				String fileName = logoPath.substring(logoPath.lastIndexOf("/") + 1) ;
				String dir9000 = "/code/wzj_Mac/idea_demo/kanglong-parent/kanglong-web/src/main/resources/static/img" ;
				//String dir9001 = "D:\\downloads\\nginx-1.6.3\\html\\9001" ;
				File f = new File(dir9000 , fileName) ;
				if(f.exists()){
					f.delete() ;
				}

//				f = new File(dir9001 , fileName) ;
//				if(f.exists()){
//					f.delete() ;
//				}
			}
			brandMapper.deleteByPrimaryKey(id) ;

		}
	}

	/**
	 * 查询所有品牌
	 */
	public List<Brand> findAll(){
		return brandMapper.selectAll() ;
	}
}
