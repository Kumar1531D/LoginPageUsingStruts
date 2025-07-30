package com.logindemo.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.logindemo.service.DatabaseService;
import com.opensymphony.xwork2.ActionSupport;

public class SignupAction extends ActionSupport{
	
	private String userName;
	private String password;
	private String email;
	
	public String execute() throws IOException{
		
		DatabaseService db = new DatabaseService();
		
		System.out.println("Inside Signup action");
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		
		if(db.insertNewUser(userName, password, email)) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
