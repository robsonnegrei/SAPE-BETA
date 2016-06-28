package com.quixada.sme.sape.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value ={ "/","/home"})
	public String index(HttpServletRequest request){
		HttpSession session = request.getSession();
		//Sem sessão, manda pro login
		if (session.getAttribute("usuario") == null) {
			return "redirect:/login";
		}
		return "index";
	}
	@RequestMapping(value = "/login",  method = { RequestMethod.GET, RequestMethod.POST })
	public String login(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		//Sem sessão, manda pro login
//		if (session.getAttribute("usuario") == null) {
//			return "/";
//		}
		return "/login";
	}

}
