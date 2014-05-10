package com.unionia.ldap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping
	public String getAdminIndexView() {
		return "admin/index";
	}

}
