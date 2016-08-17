package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quixada.sme.dao.ImagemDAO;
import com.quixada.sme.dao.PostDAO;
import com.quixada.sme.model.MetaImagem;
import com.quixada.sme.model.Post;
import com.quixada.sme.model.Usuario;

@Controller
@ComponentScan(value={"com.quixada.sme.dao"})
public class PortfolioController {
	
	public static final String REDIRECT_PORTFOLIO_INDEX = "redirect:/portfolio/index";
	public static final String PORTFOLIO_INDEX = "/portfolio/index";
	public static final String PORTFOLIO_NOVO = "/portfolio/newpost";
	
	@Autowired
	private PostDAO pDAO;
	
	@Autowired
	private ImagemDAO picDAO;
	
	@RequestMapping(value = {"portfolio/new"},method = RequestMethod.GET )
	public String novo(Model model, HttpServletRequest request, HttpSession session)
	{
		Post p = new Post();
		Usuario usr = (Usuario) session.getAttribute("usuario");
		p.setIdProfessor(usr.getIdUsuario());
		try {
			pDAO.adiciona(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		model.addAttribute("postagem", p);
		return PORTFOLIO_NOVO;
	}
	
	@RequestMapping(value = {"portfolio","portfolio/","portfolio/index"})
	public String index(Model model, HttpServletRequest request)
	{

		try {
			List<Post> postagens = pDAO.listarTodos();
			for (Post post : postagens) {
				post.setImages(picDAO.buscaPorIdPost(post.getIdPost()));
			}
			
			model.addAttribute("postagens",postagens);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return PORTFOLIO_INDEX;
	}
	
	
	@RequestMapping(value = {"portfolio/add"}, method = RequestMethod.POST)
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
			pDAO.editar(p);
		} catch (SQLException e) {
			redirect.addFlashAttribute("erro", e.getMessage());
			e.printStackTrace();
			return REDIRECT_PORTFOLIO_INDEX;
		}
		
		return REDIRECT_PORTFOLIO_INDEX;
	}
	
}
