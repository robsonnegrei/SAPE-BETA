package com.quixada.sme.sape.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quixada.sme.dao.UsuarioDAO;

@Controller
public class HomeController {
	@RequestMapping("/home")
	public String index(){
		return "index";
	}
}
