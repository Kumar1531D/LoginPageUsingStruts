package com.logindemo.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.logindemo.service.DatabaseService;
import com.logindemo.service.PasswordEncryptService;
import com.logindemo.service.TokenGenerationService;
import com.opensymphony.xwork2.ActionSupport;

public class UserAuthAction extends ActionSupport{
	
	private String userName;
	private char password[];
	private String email;
	private String token;
	
	public String login() throws IOException {
		
		System.out.println("In user auth action"+userName);
		
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
		
		return NONE;
	}
	
	public String signup() throws Exception {
		
		DatabaseService db = new DatabaseService();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		
		String encryptedPassword = PasswordEncryptService.hashWithGeneratedSalt(password);
		
		for(int i=0;i<password.length;i++) {
			password[i] = '\u0000';
		}
		
		if(db.insertNewUser(userName, encryptedPassword, email)) {
			
			response.getWriter().write("success");
		}
		else {
			response.getWriter().write("error");
		}
		
		return NONE;
	}
	
	public String authenticate() throws IOException {
		
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.toCharArray();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
