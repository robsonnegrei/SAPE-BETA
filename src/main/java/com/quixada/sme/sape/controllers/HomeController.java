package com.quixada.sme.sape.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
//	@RequestMapping(value ={ "/","/home"})
//	public String index(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		//Sem sessão, manda pro login
//		if (session.getAttribute("usuario") == null) {
//			return "redirect:/login";
//		}
//		return "index";
//	}
	@RequestMapping("/login")
	public String login(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		//Sem sessão, manda pro login
//		if (session.getAttribute("usuario") == null) {
//			return "/";
//		}
		return "/login";
	}

}
