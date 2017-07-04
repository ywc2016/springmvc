package com.buaa.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class SpringMVCService {
	public String doLogin(String username, String psw, String loginurl,
			HttpServletRequest request) {
		String result = "";
		if ((username == "") || (username == null) || (username.length() > 20)) {
			try {
				result = "请输入用户名(不能超过20个字符)!";
				request.setAttribute("message", result);
				return loginurl;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ((psw == "") || (psw == null) || (psw.length() > 20)) {
			try {
				result = "请输入密码(不能超过20个字符)!";
				request.setAttribute("message", result);
				return loginurl;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 登记JDBC驱动程序
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Class Not Found Exception . ");
		}
		// 连接URL
		String url = "jdbc:mysql://localhost:3306/learn_jsp?user=root&password=root&useUnicode=true&characterEncoding=utf-8";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
			// SQL语句
			String sql = "select * from userInfo where username='" + username
					+ "' and userpsw= '" + psw + "'";
			rs = stmt.executeQuery(sql);// 返回查询结果
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		try {
			if (rs.next()) { // 如果记录集非空，表明有匹配的用户名和密码，登陆成功
				// 登录成功后将username设置为session变量的username
				// 这样在后面就可以通过 session.getAttribute("username") 来获取用户名，
				// 同时这样还可以作为用户登录与否的判断依据
				session.setAttribute("age", rs.getString("age"));
				session.setAttribute("sex", rs.getString("sex"));
				session.setAttribute("weight", rs.getString("weight"));
				return "/success";
			} else {
				session.setAttribute("message", "用户名或密码不匹配。");
				return "/fail";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return loginurl;
	}
}