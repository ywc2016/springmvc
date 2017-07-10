package com.buaa.dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.naming.java.javaURLContextFactory;

public class UserDao extends BaseDao {

	public ResultSet getUserByUserNameAndPsw(String username, String psw) {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConn();
			stmt = conn.createStatement();
			// SQL语句
			String sql = "select * from userInfo where username='" + username
					+ "' and userpsw= '" + psw + "'";
			rs = stmt.executeQuery(sql);// 返回查询结果
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public Map showUsers(String rows, String page) {
		int intRows = ("".equals(rows) ? 5 : Integer.parseInt(rows)); // 一页显示的记录数
		int intPage = ("".equals(page) ? 1 : Integer.parseInt(page)); // 待显示的页码
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConn();
			// 创建执行语句
			String sql = "select * from userinfo limit ?,?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (intPage - 1) * intRows);
			pstmt.setInt(2, intRows);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int intRowCount = 0; // 记录的总数
		int intPageCount = 0; // 总页数

		ResultSet rs2;
		try {
			conn = getConn();
			// 创建执行语句
			String sql = "select count(*) from userinfo";
			Statement statement = conn.createStatement();
			rs2 = statement.executeQuery(sql);
			if (rs2.next()) {
				intRowCount = rs2.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取记录总数

		// 计算机总页数
		intPageCount = intRowCount / intRows
				+ (intRowCount % intRows == 0 ? 0 : 1);

		Map<String, Object> map = new HashMap<>();
		map.put("rows", rs);
		map.put("total", intPageCount);
		return map;
	}

	public String add(String username, String password, String age,
			String gender, String weight) {
		Connection conn = null;
		try {
			conn = getConn();
			// 创建执行语句
			String sql = "INSERT INTO userinfo(username,userpsw,age,weight,sex) VALUES(?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			if (age.isEmpty()) {
				pstmt.setNull(3, Types.INTEGER);
			} else {
				pstmt.setInt(3, Integer.parseInt(age));
			}
			if (weight.isEmpty()) {
				pstmt.setNull(4, Types.INTEGER);
			} else {
				pstmt.setInt(4, Integer.parseInt(weight));
			}

			pstmt.setString(5, gender);
			pstmt.executeUpdate();
			return "success";
		} catch (SQLException e) {
			e.printStackTrace();
			return "failed";
		}

	}
}
