package com.quixada.sme.sape.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quixada.sme.model.Usuario;

@Controller
public class PortfolioController {

	@RequestMapping(value = {"portfolio","portfolio/"})
	public String newUserForm(Model model, HttpServletRequest request)
	{
		
		return "portfolio/index";
	}
	
}
