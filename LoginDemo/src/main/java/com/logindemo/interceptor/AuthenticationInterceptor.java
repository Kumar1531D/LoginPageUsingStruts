package com.logindemo.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.logindemo.service.TokenGenerationService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthenticationInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		System.out.println("In interceptor");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String token = null;
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        if ("token".equals(cookie.getName())) {
		            token = cookie.getValue();
		            break;
		        }
		    }
		}
		
		if(token!=null) {
	
			TokenGenerationService tokenGenerationService  = new TokenGenerationService();
			
			String userName = tokenGenerationService.validateToken(token);
			
			if(userName != null) {
				return invocation.invoke();
			}
		}
			
		return "login";
	}
	
	

}
