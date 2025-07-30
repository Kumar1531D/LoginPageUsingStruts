package com.logindemo.action;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.logindemo.service.DatabaseService;
import com.opensymphony.xwork2.ActionSupport;


public class LoginAction extends ActionSupport{
	
	private String userName;
	private String password;
	
	public String execute() throws IOException {
		
		DatabaseService databaseService = new DatabaseService();
		
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/plain");
	 
		
		if(databaseService.loginCheck(userName, password)) {
			response.getWriter().write("success");
		}
		else {
			response.getWriter().write("error");
		}
		
		return NONE;
		
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
