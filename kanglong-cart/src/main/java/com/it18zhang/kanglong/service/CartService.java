package com.it18zhang.kanglong.service;

import com.alibaba.fastjson.JSON;
import com.it18zhang.kanglong.service.api.SpuServiceApi;
import com.it18zhang.kanglong.common.entity.Cart;
import com.it18zhang.kanglong.common.entity.Sku;
import com.it18zhang.kanglong.common.util.JWTUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 */
@Service
public class CartService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate ;

	@Autowired
	private SpuServiceApi spuServiceApi ;

	/**
	 * 添加购物车
	 */
	public void addCart(Cart cart , String token) throws Exception {
		//1.提取用户id
		Long userId = JWTUtil.parseUserIdFromToken(token) ;

		//2.定义redis中的购物车的key
		String key = "kanglong_cart_" + userId ;

		//3.绑定key的hash操作
		BoundHashOperations<String,Object,Object> hash = stringRedisTemplate.boundHashOps(key) ;
		Long skuId = cart.getSkuId() ;	//skuId
		Integer num = cart.getNum() ;	//item num
		//判断是否包含指定的skuId
		if(hash.hasKey(skuId.toString())){
			String json = hash.get(skuId.toString()).toString() ;
			cart= JSON.parseObject(json , Cart.class) ;
			cart.setNum(cart.getNum() + num);
		}
		else{
			//设置userid
			cart.setUserId(userId);
			//查询sku的具体信息
			Sku sku = spuServiceApi.findSkuBySkuId(skuId) ;
			cart.setImage(StringUtils.isBlank(sku.getImages())?"" : StringUtils.split(sku.getImages(),",")[0]);
			cart.setPrice(sku.getPrice());
			cart.setTitle(sku.getTitle());
			cart.setOwnSpec(sku.getOwnSpec());
		}
		//写入redis库中
		hash.put(cart.getSkuId()+"" , JSON.toJSONString(cart));
	}


	/**
	 * 通过用户id查询购物车
	 */
	public Map<String,Cart> findCartsByUserId(Long userId){
		Map<String,Cart> map = new HashMap<String,Cart>() ;
		String key = "kanglong_cart_" + userId;
		BoundHashOperations<String,String,String> op = stringRedisTemplate.boundHashOps(key) ;
		Set<String> keys = op.keys();
		if(keys != null){
			for(String k : keys){
				String jsonCart = op.get(k) ;
				Cart cart = JSON.parseObject(jsonCart , Cart.class) ;
				map.put(k ,cart) ;
			}
		}
		return map ;
	}

	/**
	 * 合并购物车
	 * @param carts
	 */
	public void mergeCarts(List<Cart> carts , Long userId){
		String key = "kanglong_cart_" + userId;
		BoundHashOperations<String, String, String> op = stringRedisTemplate.boundHashOps(key);
		for(Cart cart: carts){
			String skuId = cart.getSkuId() + "" ;
			String json = op.get(skuId) ;
			if(json != null){
				Cart redisCart= JSON.parseObject(json,Cart.class) ;
				redisCart.setNum(redisCart.getNum() + cart.getNum());
				op.put(skuId , JSON.toJSONString(redisCart));
			}
			else{
				op.put(skuId, JSON.toJSONString(cart));
			}
		}
	}
}
