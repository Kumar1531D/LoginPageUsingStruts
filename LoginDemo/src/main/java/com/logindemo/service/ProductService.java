package com.logindemo.service;

import java.util.*;

import com.logindemo.modelClasses.Product;

public class ProductService {

	private DbService db = new DbService();

	public List<String> getAllCategories() {
		String query = "SELECT name FROM Category";
		List<Map<String, Object>> results = db.select(query);

		List<String> categories = new ArrayList<String>();
		for (Map<String, Object> row : results) {
			categories.add(String.valueOf(row.get("name")).trim());
		}
		return categories;
	}

	public List<Product> getProductsByCategory(String categoryName) {

		String query = "SELECT p.Id as Id, p.product_name as pname, c.name as cname, a.name as aname, pa.value as pavalue FROM Product p JOIN Category c ON p.category_id = c.Id JOIN product_attribute_value pa ON p.Id = pa.product_id JOIN Attribute a ON pa.attribute_id = a.Id WHERE c.name = ? ORDER BY p.Id ";

		List<Map<String, Object>> results = db.select(query, categoryName);
		Map<Integer, Product> productMap = new LinkedHashMap<Integer, Product>();

		for (Map<String, Object> row : results) {
			Integer productId = (Integer) row.get("id");

			Product product = productMap.getOrDefault(productId, new Product());
			product.setId(productId);
			product.setProductName(String.valueOf(row.get("pname")).trim());
			product.setCategory(String.valueOf(row.get("cname")).trim());
			product.getAttributeValue().put(String.valueOf(row.get("aname")).trim(),
					String.valueOf(row.get("pavalue")).trim());

			productMap.put(productId, product);
		}

		return new ArrayList<Product>(productMap.values());
	}
}
