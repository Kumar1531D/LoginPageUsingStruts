package com.logindemo.action;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.logindemo.service.DatabaseService;
import com.logindemo.service.PasswordEncryptService;
import com.logindemo.service.TokenGenerationService;
import com.opensymphony.xwork2.ActionSupport;


public class LoginAction extends ActionSupport{
	
	private String userName;
	private char[] password;
	
	public String execute() throws IOException {
		
		DatabaseService databaseService = new DatabaseService();
		
		TokenGenerationService tokenGenerationService = new TokenGenerationService();
		
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    String token = tokenGenerationService.generateToken(userName);
	    String jsonResponse = " ";
	    
	    String encryptedPassword = databaseService.loginCheck(userName);
	    
	    boolean isPasswordCorrect ;
	    
		try {
			isPasswordCorrect = PasswordEncryptService.validatePassword(password, encryptedPassword);
		} 
		catch (Exception e) {
			isPasswordCorrect = false; 
		}
		
		for(int i=0;i<password.length;i++) {
			password[i] = '\u0000';
		}
		
		if(isPasswordCorrect) {
			jsonResponse = "{ \"status\": \"success\", \"token\": \"" + token + "\", \"userName\" : \""+userName+"\" }";	
		}
		else {
			jsonResponse = "{ \"status\": \"error\", \"message\": \" not a valid user \" }";
		}
		
		response.getWriter().write(jsonResponse);
		
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		
		return NONE;
		
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

}
