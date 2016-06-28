package com.quixada.sme.sape.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping(value ={ "/","/home"})
	public String index(){
		return "home";
	}
	@RequestMapping("/login")
	public String login(){
		return "login";
	}

}
