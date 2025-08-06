package com.logindemo.service;

import java.util.HashSet;
import java.util.Set;

public class CheckValidUrlService {
	
	private Set<String> validUrls = new HashSet<String>();
	
	public CheckValidUrlService(){
		validUrls.add("books");
		validUrls.add("electronics");
		validUrls.add("fashion");
		validUrls.add("home");
		validUrls.add("signup");
		validUrls.add("getProducts");
	}
	
	public boolean isValid(String path) {
		
		String [] paths = path.split("/");
		
		String endPoint = paths[paths.length-1];
		
		System.out.println("endpoint is "+endPoint);
		
		if(validUrls.contains(endPoint)) {
			System.out.println("true");
			return true;
		}
		
		return false;
		
	}
	
	

}
