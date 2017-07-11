package com.buaa.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.jmx.Agent;
import org.apache.naming.java.javaURLContextFactory;
import org.apache.tomcat.dbcp.dbcp2.PStmtKey;

import com.buaa.po.UserInfo;

public class UserDao extends BaseDao {

	/**
	 * 通过用户名和密码取出用户
	 * 
	 * @param username
	 * @param psw
	 * @return
	 */
	public UserInfo getUserByUserNameAndPsw(String username, String psw) {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		UserInfo userInfo = new UserInfo();
		try {
			conn = getConn();
			stmt = conn.createStatement();
			// SQL语句
			String sql = "select * from userInfo where username='" + username
					+ "' and userpsw= '" + psw + "'";
			rs = stmt.executeQuery(sql);// 返回查询结果
			if (rs.next()) {
				userInfo.setId(rs.getInt("id"));
				userInfo.setUsername(rs.getString("username"));
				userInfo.setPassword(rs.getString("userpsw"));
				userInfo.setAge(rs.getInt("age"));
				userInfo.setWeight(rs.getInt("weight"));
				userInfo.setSex(rs.getString("sex"));
			} else {
				userInfo = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userInfo;
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
		PreparedStatement pstmt = null;
		Connection conn = null;
		List<UserInfo> userInfoList = new ArrayList<>();
		try {
			conn = getConn();
			// 创建执行语句
			String sql = "select * from userinfo limit ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (intPage - 1) * intRows);
			pstmt.setInt(2, intRows);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(rs.getInt("id"));
				userInfo.setUsername(rs.getString("username"));
				userInfo.setPassword(rs.getString("userpsw"));
				userInfo.setAge(rs.getInt("age"));
				userInfo.setWeight(rs.getInt("weight"));
				userInfo.setSex(rs.getString("sex"));
				userInfoList.add(userInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		int intRowCount = 0; // 记录的总数
		int intPageCount = 0; // 总页数

		ResultSet rs2 = null;
		Statement statement = null;
		try {
			conn = getConn();
			// 创建执行语句
			String sql = "select count(*) from userinfo";
			statement = conn.createStatement();
			rs2 = statement.executeQuery(sql);
			if (rs2.next()) {
				intRowCount = rs2.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs2.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		// 获取记录总数

		// 计算总页数
		intPageCount = intRowCount / intRows
				+ (intRowCount % intRows == 0 ? 0 : 1);

		Map<String, Object> map = new HashMap<>();

		map.put("rows", userInfoList);
		map.put("total", intPageCount);
		return map;
	}

	public String add(String username, String password, String age,
			String gender, String weight) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConn();
			// 创建执行语句
			String sql = "INSERT INTO userinfo(username,userpsw,age,weight,sex) VALUES(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
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
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e2) {
			}
		}

	}

	/**
	 * 通过id查找user
	 * 
	 * @param id
	 * @return
	 */
	public UserInfo findById(String id) {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		UserInfo userInfo = new UserInfo();
		try {
			conn = getConn();
			stmt = conn.createStatement();
			// SQL语句
			String sql = "select * from userinfo where id=" + id;
			rs = stmt.executeQuery(sql);// 返回查询结果
			if (rs.next()) {
				userInfo.setId(rs.getInt("id"));
				userInfo.setUsername(rs.getString("username"));
				userInfo.setPassword(rs.getString("userpsw"));
				userInfo.setAge(rs.getInt("age"));
				userInfo.setWeight(rs.getInt("weight"));
				userInfo.setSex(rs.getString("sex"));
			} else {
				userInfo = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userInfo;
	}

	public String update(String id, String username, String age, String weight,
			String sex) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int result;
		try {
			conn = getConn();
			// SQL语句
			String sql = "UPDATE userinfo SET username = ?,age = ?,weight = ?,sex=? where id = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			if (age.isEmpty()) {
				pstmt.setNull(2, Types.INTEGER);
			} else {
				pstmt.setInt(2, Integer.parseInt(age));
			}
			if (weight.isEmpty()) {
				pstmt.setNull(3, Types.INTEGER);
			} else {
				pstmt.setInt(3, Integer.parseInt(weight));
			}
			pstmt.setString(4, sex);
			pstmt.setInt(5, Integer.parseInt(id));

			result = pstmt.executeUpdate();
			return "success";
		} catch (SQLException e) {
			e.printStackTrace();
			return "failed";
		} finally {

			try {
				pstmt.close();
				conn.close();
			} catch (Exception e2) {
			}
		}

	}

	public String deleteById(String id) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		int result;
		try {
			conn = getConn();
			// SQL语句
			String sql = "DELETE FROM userinfo where id = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			result = pstmt.executeUpdate();
			return "success";
		} catch (SQLException e) {
			e.printStackTrace();
			return "failed";
		} finally {

			try {
				pstmt.close();
				conn.close();
			} catch (Exception e2) {
			}
		}

	}
}
