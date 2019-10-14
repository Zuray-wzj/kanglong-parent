package com.it18zhang.kanglong.service;

import com.it18zhang.kanglong.common.entity.Sku;
import com.it18zhang.kanglong.common.entity.Spu;
import com.it18zhang.kanglong.common.entity.SpuDetail;
import com.it18zhang.kanglong.common.vo.SpuVO;
import com.it18zhang.kanglong.mapper.SkuMapper;
import com.it18zhang.kanglong.mapper.SpuDetailMapper;
import com.it18zhang.kanglong.mapper.SpuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品服务
 */
@Service
public class SpuService {

	@Autowired
	private SpuMapper spuMapper ;
	@Autowired
	private SpuDetailMapper spuDetailMapper;
	@Autowired
	private SkuMapper skuMapper ;

	@Autowired
	private CategoryService categoryService ;

	/**
	 * 查询商品列表
	 */
	public List<SpuVO> findSpus(){
		return spuMapper.findSpus() ;
	}

	/**
	 * 保存或更新Spu
	 */
	@Transactional
	public void saveOrUpdate(Spu spu) {
		Long cid2 = categoryService.findById(spu.getCid3()).getParentId() ;
		Long cid1 = categoryService.findById(cid2).getParentId() ;
		spu.setCid1(cid1);
		spu.setCid2(cid2);

		//插入
		if(spu.getId() == null){
			spuMapper.insert(spu) ;
			//detail
			SpuDetail detail = spu.getSpuDetail() ;
			detail.setSpuId(spu.getId());
			spuDetailMapper.insert(detail) ;

			//保存sku集合
			for(Sku sku : spu.getSkus()){
				sku.setSpuId(spu.getId());
				skuMapper.insert(sku) ;
			}
		}
		//更新
		else{
			spuMapper.updateByPrimaryKey(spu) ;
			//detail
			SpuDetail detail = spu.getSpuDetail() ;
			detail.setSpuId(spu.getId());
			spuDetailMapper.updateByPrimaryKey(detail) ;
			//skus
			for(Sku sku : spu.getSkus()){
				sku.setSpuId(spu.getId());
				if(sku.getId() == null){
					skuMapper.insert(sku) ;
				}
				else{
					skuMapper.updateByPrimaryKey(sku) ;
				}
			}
		}
	}

	/**
	 * 删除商品
	 */
	public void deleteById(Long id){
		skuMapper.deleteBySpuId(id) ;
		spuDetailMapper.deleteBySpuId(id) ;
		spuMapper.deleteByPrimaryKey(id) ;
	}

	/**
	 * 按照id查询spu
	 */
	public Spu findById(Long id) {
		//查询spu
		Spu spu = spuMapper.selectByPrimaryKey(id) ;
		//查询detail
		SpuDetail detail = spuDetailMapper.selectByPrimaryKey(id) ;
		//按照spuid查询sku
		Sku sku = new Sku() ;
		sku.setSpuId(id);

		//组装集合
		List<Sku> list = skuMapper.select(sku) ;
		spu.setSpuDetail(detail);
		spu.setSkus(list);
		return spu ;
	}
}


