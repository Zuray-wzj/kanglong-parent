package com.it18zhang.kanglong.service;

import com.it18zhang.kanglong.common.entity.User;
import com.it18zhang.kanglong.mapper.UserMapper;
import org.apache.lucene.codecs.CodecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

/**
 * UserService
 */
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper ;

	@Autowired
	private StringRedisTemplate stringRedisTemplate ;

	/**
	 * 检查数据
	 * 1:username 2:phone
	 */
	public boolean checkData(String data, int type) {
		User u = new User() ;
		if(type == 1){
			u.setUsername(data);
		}
		else{
			u.setPhone(data);
		}
		return userMapper.selectCount(u) == 0 ;
	}

	/**
	 * 注册用户
	 */
	public boolean register(User user, String verifyCode) {
		//1.判断验证码
		String redisCode = stringRedisTemplate.boundValueOps(user.getPhone()).get();
		if(!verifyCode.equals(redisCode)){
			return false ;
		}
		//2.使用md5密码加密
		String md5pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5pwd);

		//3.注册时间
		user.setCreated(new Date());
		boolean b = userMapper.insert(user) == 1 ;
		//注册成功
		if(b){
			//删除redis中的验证码
			stringRedisTemplate.delete(user.getPhone()) ;
		}
		return b ;
	}

	/**
	 * 按照username和password进行查询
	 */
	public User query(String username, String password) {
		User u = new User() ;
		u.setUsername(username);
		u.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
		return userMapper.selectOne(u) ;
	}
}
