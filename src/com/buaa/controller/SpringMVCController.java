package com.buaa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buaa.service.SpringMVCService;

@Controller
public class SpringMVCController {

	@Autowired
	HttpServletRequest request;

	@RequestMapping("/login")
	public String dologin() {

		SpringMVCService service = new SpringMVCService();

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