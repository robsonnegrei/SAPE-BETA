package com.quixada.sme.sape.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminMainController {

	@RequestMapping("admin/index")
	public String adminIndex(HttpServletRequest request, HttpSession session){
		if (session.getAttribute("usuario") == null) {
			return "redirect:../login";
		}
		return "admin/index";
	}
	@RequestMapping("admin/logout")
	public String logout(HttpServletRequest request, HttpSession session){
		session.invalidate();
		return "redirect:../login";
	}
}
