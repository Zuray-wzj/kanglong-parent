package com.it18zhang.kanglong.common.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 鉴权工具类
 */
public class JWTUtil {
	private static byte[] secret = "geiwodiangasfdjsikolkjikolkijswe".getBytes() ;
	/**
	 * 对指定的map消息生成令牌
	 */
	public static String genToken(Map<String,Object> map){
		try {
			//header信息
			JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
			//负载信息
			Payload payload = new Payload(new JSONObject(map));
			//将头部和载荷结合在一起
			JWSObject jwsObject = new JWSObject(jwsHeader, payload);
			//建立一个密匙
			JWSSigner jwsSigner = new MACSigner(secret);
			//签名
			jwsObject.sign(jwsSigner);
			return jwsObject.serialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}

	/**
	 * 解析令牌
	 */
	public static String parseToken(String token){
		try {
			JWSObject jwsObject = JWSObject.parse(token);
			//获取到载荷
			Payload payload = jwsObject.getPayload();

			//建立一个解锁密匙
			JWSVerifier jwsVerifier = new MACVerifier(secret);

			Map<String, Object> resultMap = new HashMap<>();
			//判断token
			if (jwsObject.verify(jwsVerifier)) {
				//载荷的数据解析成json对象。
				JSONObject jsonObject = payload.toJSONObject();
				return jsonObject.toJSONString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}

	/**
	 * 从加密的token中解析出userId
	 */
	public static Long parseUserIdFromToken(String token) throws Exception {
		//1.base62x解码
		token = Base62x.decode(token) ;
		//2.解密
		token = RsaUtil.decryptWithKey(token , RsaUtil.publicKeyString,true) ;
		//
		JWSObject jwsObject = JWSObject.parse(token);
		//获取到载荷
		Payload payload = jwsObject.getPayload();

		//建立一个解锁密匙
		JWSVerifier jwsVerifier = new MACVerifier(secret);
		//判断token
		if (jwsObject.verify(jwsVerifier)) {
			//载荷的数据解析成json对象。
			JSONObject jsonObject = payload.toJSONObject();
			return (Long)jsonObject.getAsNumber("id") ;
		}
		return null;
	}
}
