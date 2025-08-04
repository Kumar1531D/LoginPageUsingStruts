package com.logindemo.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.logindemo.service.DatabaseService;
import com.logindemo.service.PasswordEncryptService;
import com.opensymphony.xwork2.ActionSupport;

public class SignupAction extends ActionSupport{
	
	private String userName;
	private char[] password;
	private String email;
	
	public String execute() throws Exception{
		
		DatabaseService db = new DatabaseService();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		
		String encryptedPassword = PasswordEncryptService.hashWithGeneratedSalt(password);
		
		for(int i=0;i<password.length;i++) {
			password[i] = '\u0000';
		}
		
//		throw new SQLException();
		
		if(db.insertNewUser(userName, encryptedPassword, email)) {
			
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}
	

}
