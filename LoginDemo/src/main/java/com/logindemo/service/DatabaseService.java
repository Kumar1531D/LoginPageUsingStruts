package com.logindemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService {
	
	private String url = "jdbc:postgresql://localhost:5432/DemoDb";
	private String user = "muthu-24209";
	private String pass = "stupyfy";
	
	public boolean loginCheck(String userName, String password) {
		
		Connection con = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			con = DriverManager.getConnection(url,user,pass);
			
			String query = "select Username from UserDetail where Username = ? and password = ?;";
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, userName);
			ps.setString(2,password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
			return false;
			
			
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
		
		return false;
		
	}
	
	public boolean insertNewUser(String userName,String password,String email) {
		
		Connection con = null;
		
		System.out.println("Insert a new user "+userName);
		
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

		return true;
		
	}
	

}
