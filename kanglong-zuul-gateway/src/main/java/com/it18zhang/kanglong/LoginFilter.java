package com.it18zhang.kanglong;

import com.it18zhang.kanglong.common.util.Base62x;
import com.it18zhang.kanglong.common.util.RsaUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 登录过滤
 */
public class LoginFilter extends ZuulFilter{
	public String filterType() {
		return "pre";
	}

	public int filterOrder() {
		return 0;
	}

	public boolean shouldFilter() {
		System.out.println("===========================>");
		HttpServletRequest req = RequestContext.getCurrentContext().getRequest();
		String url = req.getRequestURL().toString();
		try {
			URL url0 = new URL(url) ;
			String path = url0.getPath() ;
			if(path.startsWith("/user")){
				return false ;
			}
			else if(path.startsWith("/order")){
				return true ;
			}
			System.out.println(path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return true;
	}

	public Object run() throws ZuulException {
		HttpServletRequest req = RequestContext.getCurrentContext().getRequest() ;
		String token = req.getParameter("token") ;
		if(token == null || token.equals("")){
			throw new ZuulException("没有登录" , 400 , "请登录！！") ;
		}
		else{
			try {
				//1.先进行62x解码
				String dec62xToken = Base62x.decode(token) ;
				//2.进行rsa解密
				RsaUtil.decryptWithKey(dec62xToken , RsaUtil.publicKeyString,true) ;
			} catch (Exception e) {
				throw new ZuulException("token无效", 400, "token无效！！");
			}
		}
		return null;
	}

}
