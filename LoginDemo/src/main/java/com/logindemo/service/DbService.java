package com.logindemo.service;

import java.sql.*;
import java.util.*;

public class DbService {

	private static final String URL = "jdbc:postgresql://localhost:5432/DemoDb";
	private static final String USER = "muthu-24209";
	private static final String PASS = "stupyfy";

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("PostgreSQL Driver not found", e);
		}
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}

	public List<Map<String, Object>> select(String query, Object... params) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		try {

			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(query);

			setParams(ps, params);
			ResultSet rs = ps.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();

			while (rs.next()) {
				Map<String, Object> row = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					row.put(meta.getColumnLabel(i), rs.getObject(i));
				}
				resultList.add(row);
			}

		} catch (SQLException e) {
			e.printStackTrace(); // You can improve with custom logging
		}

		return resultList;
	}

	public int update(String query, Object... params) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(query);

			setParams(ps, params);
			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private void setParams(PreparedStatement ps, Object... params) throws SQLException {
		for (int i = 0; i < params.length; i++) {
			ps.setObject(i + 1, params[i]);
		}
	}
}
