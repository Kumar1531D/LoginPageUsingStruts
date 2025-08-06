package com.logindemo.service;

import java.util.List;
import java.util.Map;

public class UserService {
	
    private DbService db = new DbService();

    public String getPasswordForUser(String username) {
        String query = "SELECT password FROM UserDetail WHERE Username = ?";
        List<Map<String, Object>> results = db.select(query, username);

        if (!results.isEmpty()) {
            return String.valueOf(results.get(0).get("password"));
        }
        return null;
    }

    public boolean createUser(String username, String password, String email) {
        String query = "INSERT INTO UserDetail(Username, password, email) VALUES (?, ?, ?)";
        return db.update(query, username, password, email) > 0;
    }
    
    public boolean isUsernameExists(String username) {
        String query = "SELECT 1 FROM UserDetail WHERE Username = ?";
        return !db.select(query, username).isEmpty();
    }

    public boolean isEmailExists(String email) {
        String query = "SELECT 1 FROM UserDetail WHERE email = ?";
        return !db.select(query, email).isEmpty();
    }

}
