package com.logindemo.action;

import java.sql.SQLException;
import java.util.List;

import com.logindemo.modelClasses.Product;
import com.logindemo.service.DatabaseService;
import com.opensymphony.xwork2.ActionSupport;

public class ProductAction extends ActionSupport{
	
	private String category;
	private List<Product> products;
	
	public String execute() throws SQLException {
		
		DatabaseService databaseServie = new DatabaseService();
		
		products = databaseServie.getProductsByCategory(category);
		
		return SUCCESS;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	

}
