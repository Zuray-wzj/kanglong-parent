package com.it18zhang.kanglong.controller;

import com.aliyuncs.exceptions.ClientException;
import com.it18zhang.kanglong.common.entity.Cart;
import com.it18zhang.kanglong.common.entity.User;
import com.it18zhang.kanglong.common.util.Base62x;
import com.it18zhang.kanglong.common.util.JWTUtil;
import com.it18zhang.kanglong.common.util.RsaUtil;
import com.it18zhang.kanglong.service.SmsSendService;
import com.it18zhang.kanglong.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * UserController
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService ;

	@Autowired
	private SmsSendService smsSendService ;

	@Autowired
	private StringRedisTemplate stringRedisTemplate ;

	@Value("${redis.verifycode.expire.seconds}")
	private int expires ;

	@GetMapping("/check/{data}/{type}")
	public boolean checkData(@PathVariable("data") String data ,@PathVariable("type") int type){
		return userService.checkData(data, type) ;
	}

	/**
	 * 发送验证码给客户端，同时写入redis
	 */
	@GetMapping("/verifycode/{phone}")
	public void sendVerifyCode(@PathVariable("phone") String phone) throws ClientException {
		//1.生成验证码
		String verifyCode = genVerifyCode() ;
		//2.发送验证码给客户端
		//smsSendService.sendSms(phone , verifyCode , "" ,"") ;
		//3.验证码连同phone写入redis
		stringRedisTemplate.boundValueOps(phone).set(verifyCode , expires , TimeUnit.SECONDS);
	}

	/**
	 * 生成验证码，通常为6位数。
	 */
	private String genVerifyCode() {
		Random r = new Random() ;
		//999999
		int i = r.nextInt(1000000) ;
		DecimalFormat df = new DecimalFormat("000000") ;
		return df.format(i) ;
	}

	@PostMapping("/register")
	public boolean register(User user ,@RequestParam("code") String verifyCode){
		return userService.register(user , verifyCode) ;
	}

	/**
	 * 查询用户
	 */
	@PostMapping("/query")
	public String query(@RequestParam(value = "username" ,defaultValue = "") String username , @RequestParam(value = "password",defaultValue = "") String password){
		User u = userService.query(username,password) ;
		if(u != null){
			Long id = u.getId() ;
			String name = u.getUsername() ;
			Map<String,Object> map = new HashMap<String,Object>() ;
			map.put("id" , id) ;
			map.put("name" , name) ;
			String token = JWTUtil.genToken(map) ;
			token = RsaUtil.encryptWithKey(token , RsaUtil.privateKeyString , false) ;
			//对产生的token再进行62x编码，消掉符号(+-/=等)
			token = Base62x.encode(token);
			System.out.println("login gen's token: " + token);
			return token ;
		}
		else{
			throw new RuntimeException("查询失败!!") ;
		}
	}

	/**
	 * 验证用户是否登录
	 */
	@GetMapping("/verify")
	public void verify(@RequestParam(value = "token" ,defaultValue = "") String token){
		System.out.println("recved token : " + token);
		try {
			if (token != null && !token.equals("")) {
				//先使用62x解码
				String dec62xtoken = Base62x.decode(token) ;
				//再使用rsa解密
				RsaUtil.decryptWithKey(dec62xtoken, RsaUtil.publicKeyString, true);
			}
			else{
				throw new RuntimeException("token无效，没有登录！！");
			}
		} catch (Exception e) {
			throw new RuntimeException("token无效，没有登录！！") ;
		}
	}
}
