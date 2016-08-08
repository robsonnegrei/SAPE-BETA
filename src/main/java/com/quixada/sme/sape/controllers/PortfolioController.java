package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quixada.sme.dao.PostDAO;
import com.quixada.sme.model.Post;
import com.quixada.sme.model.Usuario;

@Controller
@ComponentScan(value={"com.quixada.sme.dao"})
public class PortfolioController {
	
	public static final String REDIRECT_PORTFOLIO_INDEX = "redirect:/portfolio/index";
	public static final String PORTFOLIO_INDEX = "/portfolio/index";
	
	@Autowired
	private PostDAO pDAO;
	
	@RequestMapping(value = {"portfolio","portfolio/","portfolio/index"})
	public String index(Model model, HttpServletRequest request)
	{
		Post p = new Post();
		model.addAttribute("postagem", p);
		
		try {
			model.addAttribute("postagens",pDAO.listar());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return PORTFOLIO_INDEX;
	}
	
	
	@RequestMapping(value = {"portfolio/add"})
	public String addPost(@Validated @ModelAttribute(value="postagem") Post p, 
	BindingResult bindingResult,
	Model model,
	RedirectAttributes redirect,
	HttpSession session)
	{
		if (bindingResult.hasErrors()) {
			redirect.addFlashAttribute("erro",bindingResult.getAllErrors().get(0).getDefaultMessage());
			return REDIRECT_PORTFOLIO_INDEX;
		}
		
		Usuario usr = (Usuario) session.getAttribute("usuario");
		p.setIdProfessor(usr.getIdUsuario());
		LocalDateTime agora = LocalDateTime.now();
		java.sql.Timestamp sqlDate = Timestamp.valueOf(agora);
		p.setData(sqlDate);
		try {
			pDAO.adiciona(p);
		} catch (SQLException e) {
			redirect.addFlashAttribute("erro", e.getMessage());
			e.printStackTrace();
			return REDIRECT_PORTFOLIO_INDEX;
		}
		
		return REDIRECT_PORTFOLIO_INDEX;
	}
}
