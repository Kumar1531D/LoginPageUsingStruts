package com.logindemo.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.logindemo.service.CheckValidUrlService;
import com.opensymphony.xwork2.ActionSupport;

public class UrlRedirectionAction extends ActionSupport{
	
	public String execute() {
		
		HttpServletRequest request = ServletActionContext.getRequest();

        String path = request.getRequestURI();  
        
		if(new CheckValidUrlService().isValid(path)) {
			return SUCCESS;
		}
		
		return ERROR;
	}

}
