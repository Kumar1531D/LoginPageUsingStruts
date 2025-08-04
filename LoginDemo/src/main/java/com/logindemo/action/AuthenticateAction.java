package com.logindemo.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.logindemo.service.TokenGenerationService;
import com.opensymphony.xwork2.ActionSupport;

public class AuthenticateAction extends ActionSupport{
	
	private String token;
	
	@Override
	public String execute() throws IOException {
		
		TokenGenerationService tokenGenerationService  = new TokenGenerationService();
		
		String userName = tokenGenerationService.validateToken(token);
		
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    String jsonResponse = "";
		
		if(userName==null) {
			jsonResponse = "{ \" status \" : \" error \" }";
		}
		else {
			jsonResponse = "{ \" status \" : \" success \" }";
		}
		
		response.getWriter().write(jsonResponse);
		
		return NONE;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
