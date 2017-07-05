package com.buaa.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.buaa.service.UserService;

@Controller
public class UserController {

	private UserService userService = new UserService();

	@Autowired
	HttpServletRequest request;

	@RequestMapping("/")
	public String hello() {
		return "/login";
	}

	@RequestMapping("/login")
	public String dologin() {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") != null
				&& !session.getAttribute("username").toString().isEmpty()) {// 已经登录
			return "/success";
		}

		String username = request.getParameter("username");
		String psw = request.getParameter("password");

		return userService.doLogin(username, psw, "/login", request);

	}

	@RequestMapping("/showUsers")
	public String showUsers(
			@RequestParam(value = "rows", defaultValue = "5") String rows,
			@RequestParam(value = "page", defaultValue = "1") String page,
			Model model) {
		Map<String, Object> map = userService.showUsers(rows, page);
		model.addAllAttributes(map);
		model.addAttribute("currentPage", page);
		return "showUsers";
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout() {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") != null
				&& !session.getAttribute("username").toString().isEmpty()) {
			session.removeAttribute("username");
			session.removeAttribute("age");
			session.removeAttribute("sex");
			session.removeAttribute("weight");
			// System.out.println(session.getAttribute("username"));//返回值是null
		}
		return "/login";
	}
}