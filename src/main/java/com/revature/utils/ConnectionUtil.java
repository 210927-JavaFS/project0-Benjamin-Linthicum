package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "javafs-210927-project-0.ci49jbdfz59e.us-east-2.rds.amazonaws.com";
		String username = "postgres";
		String password = "password";
		
		return DriverManager.getConnection(url, username, password);
	}
}
