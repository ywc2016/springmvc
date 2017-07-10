package com.buaa.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.buaa.dao.UserDao;

@Service
public class UserService {
	private UserDao userDao = new UserDao();

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

		ResultSet rs = userDao.getUserByUserNameAndPsw(username, psw);
		HttpSession session = request.getSession();
		try {
			if (rs.next()) { // 如果记录集非空，表明有匹配的用户名和密码，登陆成功
				// 登录成功后将username设置为session变量的username
				// 这样在后面就可以通过 session.getAttribute("username") 来获取用户名，
				// 同时这样还可以作为用户登录与否的判断依据
				session.setAttribute("age", rs.getString("age"));
				session.setAttribute("sex", rs.getString("sex"));
				session.setAttribute("weight", rs.getString("weight"));
				session.setAttribute("username", username);
				return "/success";
			} else {
				session.setAttribute("message", "用户名或密码不匹配。");
				return "/fail";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return loginurl;
	}

	public Map<String, Object> showUsers(String rows, String page) {
		return userDao.showUsers(rows, page);
	}

	public String add(HttpServletRequest request) {

		String username = request.getParameter("username");
		String passwordString = request.getParameter("password");
		String age = request.getParameter("age");
		String gender = request.getParameter("gender");
		String weight = request.getParameter("weight");
		String result = userDao.add(username, passwordString, age,
				gender, weight);
		return result;
	}
}