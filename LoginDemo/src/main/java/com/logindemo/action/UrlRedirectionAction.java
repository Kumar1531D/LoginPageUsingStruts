package com.logindemo.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UrlRedirectionAction extends ActionSupport{
	
	public String execute() {
		
		HttpServletRequest request = ServletActionContext.getRequest();

        String fullUrl = request.getRequestURL().toString();  // Full URL
        String path = request.getRequestURI();  
        
        
		if(path.endsWith("books") || path.endsWith("electronics") || path.endsWith("fashion") || path.endsWith("home") )
			return SUCCESS;
		
		return ERROR;
	}

}
