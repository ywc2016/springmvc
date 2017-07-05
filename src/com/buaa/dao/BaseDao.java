package com.buaa.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseDao {
	private Connection conn = null;

	public Connection getConn() {

		if (conn != null) {
			return conn;
		}
		// 登记JDBC驱动程序
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 连接URL
			String url = "jdbc:mysql://localhost:3306/learn_jsp?user=root&password=root&useUnicode=true&characterEncoding=utf-8";
			Connection conn = null;
			conn = DriverManager.getConnection(url);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
