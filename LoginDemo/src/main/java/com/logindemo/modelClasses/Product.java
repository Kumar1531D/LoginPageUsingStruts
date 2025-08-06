package com.logindemo.modelClasses;

import java.util.HashMap;
import java.util.Map;

public class Product {
	
	private int id;
	private String category;
	private String productName;
	private Map<String,String> attributeValue = new HashMap<String,String>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Map<String, String> getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(Map<String, String> attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	

}
