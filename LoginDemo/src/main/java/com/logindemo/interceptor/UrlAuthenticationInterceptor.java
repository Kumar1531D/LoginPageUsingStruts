package com.logindemo.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.logindemo.service.CheckValidUrlService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UrlAuthenticationInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("In url authentication interceptor");
		
		HttpServletRequest request = ServletActionContext.getRequest();

        String path = request.getRequestURI();
        
        String [] paths = path.split("/");
		
		String endPoint = paths[paths.length-1];
		
		if(endPoint.equals("login") || endPoint.equals("signup")) {
			System.out.println("In login");
			return "login";
		}
        
		if(new CheckValidUrlService().isValid(path)) {
			return "success";
		}
		
		return "error";
	}

}
