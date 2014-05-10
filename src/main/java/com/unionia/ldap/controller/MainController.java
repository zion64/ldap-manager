package com.unionia.ldap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String getIndexView() {
		return "index";
	}

	@RequestMapping("/login")
	public String getLoginView() {
		return "login";
	}
	
	@RequestMapping("/loginfailed")
	public String showLoginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}
}
