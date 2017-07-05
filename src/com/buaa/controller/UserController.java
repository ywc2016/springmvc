package com.buaa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buaa.service.UserService;

@Controller
public class UserController {

	@Autowired
	HttpServletRequest request;

	@RequestMapping("/login")
	public String dologin() {

		UserService service = new UserService();

		String username = request.getParameter("username");
		String psw = request.getParameter("password");

		return service
				.doLogin(username, psw, "/login", request);

	}

	@RequestMapping("/")
	public String hello() {
		return "/login";
	}
}