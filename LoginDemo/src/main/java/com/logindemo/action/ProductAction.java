package com.logindemo.action;

import java.sql.SQLException;
import java.util.List;

import com.logindemo.modelClasses.Product;
import com.logindemo.service.DatabaseService;
import com.logindemo.service.ProductService;
import com.opensymphony.xwork2.ActionSupport;

public class ProductAction extends ActionSupport{
	
	private String category;
	private List<Product> products;
	private List<String> categoryList;
	
	public String getProductsList() throws SQLException {
		
		//DatabaseService databaseServie = new DatabaseService();
		
		ProductService db = new ProductService();
		
		products = db.getProductsByCategory(category);
		
		return SUCCESS;
	}
	
	public String getCategoryLists() {
		
		//DatabaseService databaseService = new DatabaseService();
		ProductService db = new ProductService();
		
		categoryList = db.getAllCategories();
		
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

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}
	
	public List<String> getCategoryList(){
		return categoryList;
	}
	

}
