package com.logindemo.action;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.logindemo.service.DatabaseService;
import com.logindemo.service.PasswordEncryptService;
import com.logindemo.service.TokenGenerationService;
import com.logindemo.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class UserAuthAction extends ActionSupport{
	
	private String userName;
	private char password[];
	private String email;
	private String token;
	
	public String login() throws IOException {
		
		//DatabaseService databaseService = new DatabaseService();
		
		UserService userService = new UserService();
		
		TokenGenerationService tokenGenerationService = new TokenGenerationService();
		
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    String token = tokenGenerationService.generateToken(userName);
	    String jsonResponse = " ";
	    
	   // String encryptedPassword = databaseService.loginCheck(userName);
	    
	    String encryptedPassword = userService.getPasswordForUser(userName);
	    
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
		
		String contextPath = ServletActionContext.getRequest().getContextPath();

		Cookie jwtCookie = new Cookie("token", token);
		jwtCookie.setHttpOnly(true);
		jwtCookie.setSecure(true);
		jwtCookie.setPath(contextPath); // IMPORTANT
		jwtCookie.setMaxAge(24 * 60 * 60); 

		response.addCookie(jwtCookie);
		
		response.getWriter().write(jsonResponse);
		
		return NONE;
	}
	
	public String signup() throws Exception {
		
		//DatabaseService db = new DatabaseService();
		
		UserService userService = new UserService();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		
		String encryptedPassword = PasswordEncryptService.hashWithGeneratedSalt(password);
		
		for(int i=0;i<password.length;i++) {
			password[i] = '\u0000';
		}
		
		System.out.println(userName +" "+email);
		
		if(userService.isEmailExists(email) ) {
			response.getWriter().write("emailError");
		}
		else if(userService.isUsernameExists(userName)) {
			response.getWriter().write("userNameError");
		}
		else if(userService.createUser(userName, encryptedPassword, email)) {
			
			response.getWriter().write("success");
		}
		else {
			response.getWriter().write("error");
		}
		
		return NONE;
	}
	
	public String authenticate() throws IOException {
		
		System.out.println("In authenticate");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
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
			
			if(userName != null)
				return SUCCESS;
		}
		
		return ERROR;
	}
	
	public String logout() {
		HttpServletResponse response = ServletActionContext.getResponse();

		String contextPath = ServletActionContext.getRequest().getContextPath();

		Cookie cookie = new Cookie("token", "");
		cookie.setMaxAge(0);
		cookie.setPath(contextPath); // MUST match!
		response.addCookie(cookie);

	    return "login";
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
