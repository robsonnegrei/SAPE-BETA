package com.quixada.sme.sape.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quixada.sme.model.Post;
import com.quixada.sme.model.Usuario;

@Controller
public class PortfolioController {

	@RequestMapping(value = {"portfolio","portfolio/"})
	public String newUserForm(Model model, HttpServletRequest request)
	{
		Post p = new Post();
		model.addAttribute("newPost", p);
		return "portfolio/index";
	}
	
}
