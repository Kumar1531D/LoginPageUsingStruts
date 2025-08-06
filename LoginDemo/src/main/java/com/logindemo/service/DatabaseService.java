package com.logindemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.logindemo.modelClasses.Product;

public class DatabaseService {
	
	private String url = "jdbc:postgresql://localhost:5432/DemoDb";
	private String user = "muthu-24209";
	private String pass = "stupyfy";
	
	public String loginCheck(String userName) {
		
		Connection con = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			con = DriverManager.getConnection(url,user,pass);
			
			String query = "select password from UserDetail where Username = ?;";
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, userName);
//			ps.setString(2,password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				System.out.println(rs.getString("password"));
				return rs.getString("password");
			}
			
			return null;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if (con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
		
	}
	
	public boolean insertNewUser(String userName,String password,String email) {
		
		Connection con = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			con = DriverManager.getConnection(url,user,pass);
			
			String qry = "insert into UserDetail(Username,password,email) values(?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(qry);
			
			ps.setString(1, userName);
			ps.setString(2, password);
			ps.setString(3, email);
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected>0) {
				return true;
			}
			
			return false;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return false;
		
	}
	
	public List<Product> getProductsByCategory(String categoryName) throws SQLException {
		
		Connection con = null;
		Map<Integer, Product> productMap = new LinkedHashMap<Integer,Product>();
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			con = DriverManager.getConnection(url,user,pass);
	    
		    String sql = "SELECT p.Id as Id, p.product_name as pname, c.name as cname, a.name as aname, pa.value as pavalue FROM Product p JOIN Category c ON p.category_id = c.Id JOIN product_attribute_value pa ON p.Id = pa.product_id JOIN Attribute a ON pa.attribute_id = a.Id WHERE c.name = ? ORDER BY p.Id ";
	
		    PreparedStatement ps = con.prepareStatement(sql);
		    ps.setString(1, categoryName);
	
		    ResultSet rs = ps.executeQuery();
		    
	
		    while (rs.next()) {
		        int productId = rs.getInt("Id");
	
		        Product product = productMap.getOrDefault(productId, new Product());
		        product.setId(productId);
		        product.setProductName(rs.getString("pname").trim());
		        product.setCategory(rs.getString("cname").trim());
		        product.getAttributeValue().put(rs.getString("aname").trim(), rs.getString("pavalue").trim());
	
		        productMap.put(productId, product);
		    }
		    
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if (con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	    return new ArrayList<Product>(productMap.values());
	    
	}

	

}
