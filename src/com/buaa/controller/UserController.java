package com.buaa.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.buaa.po.UserInfo;
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

		return userService.doLogin(username, psw, request);

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

	@RequestMapping(value = "/addUser")
	public String addUser() {
		return "/addUser";
	}

	@RequestMapping(value = "/add")
	public String add(Model model) {
		String result = userService.add(request);
		if (result.equals("success")) {
			model.addAttribute("result", "添加成功!");
		} else {
			model.addAttribute("result", "添加失败!");
		}
		return "addResult";
	}

	@RequestMapping("/modifyUser")
	public String modifyUser(Model model,
			@RequestParam(value = "id", required = true) String id) {
		UserInfo userInfo = userService.findById(id);
		model.addAttribute("userInfo", userInfo);
		return "modifyUser";
	}

	@RequestMapping("/save")
	public String modifyUser(
			Model model,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "username", defaultValue = "") String username,
			@RequestParam(value = "age", defaultValue = "") String age,
			@RequestParam(value = "weight", defaultValue = "") String weight,
			@RequestParam(value = "sex", defaultValue = "") String sex) {
		String result = userService.update(id, username, age, weight, sex);
		if (result.equals("success")) {
			model.addAttribute("result", "修改成功!");
		} else {
			model.addAttribute("result", "修改失败!");
		}

		return "modifyUserResult";
	}

	@RequestMapping("/deleteUser")
	public String deleteUser(Model model,
			@RequestParam(value = "id", required = true) String id) {
		String result = userService.deleteById(id);
		model.addAttribute("result", result.equals("success") ? "删除成功" : "删除失败");
		return "deleteUserResult";
	}
}