package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.quixada.sme.dao.PCleiDAO;
import com.quixada.sme.dao.PostDAO;
import com.quixada.sme.model.MetaImagem;
import com.quixada.sme.model.PClei;
import com.quixada.sme.model.Post;
import com.quixada.sme.model.Usuario;

@Controller
@ComponentScan(value={"com.quixada.sme.dao"})
public class PortfolioController {
	
	private static Logger logger = LoggerFactory.getLogger(PortfolioController.class);
	
	public static final String REDIRECT_PORTFOLIO_INDEX = "redirect:/portfolio/index";
	public static final String PORTFOLIO_INDEX = "/portfolio/index";
	public static final String PORTFOLIO_NOVO = "/portfolio/newpost";
	
	@Autowired
	private PostDAO pDAO;
	
	@Autowired
	private ImagemDAO picDAO;
	
	@Autowired private PCleiDAO pcDAO;
	
	@RequestMapping(value = {"portfolio/new"},method = RequestMethod.GET )
	public String novo(Model model, HttpServletRequest request, HttpSession session, RedirectAttributes redirect)
	{
		Post p = new Post();
		Usuario usr = (Usuario) session.getAttribute("usuario");
		logger.info("solicitada nova postagem para: " + usr.getEmail());
		try {
			PClei lei = pcDAO.buscaPorIdUsuario(usr.getIdUsuario());
			if (lei==null) {
				redirect.addFlashAttribute("erro","Sua conta não permite escrever posts.");
				return REDIRECT_PORTFOLIO_INDEX;
			}
			p.setIdProfessor(lei.getIdProfessor());
			pDAO.adiciona(p);
		} catch (SQLException e) {
			logger.error("novo post: " + e.getMessage());
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
			logger.error("index portfolio : " + e.getMessage());
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
		logger.info("persistir postagem " + p.getIdPost());
		if (bindingResult.hasErrors()) {
			redirect.addFlashAttribute("erro",bindingResult.getAllErrors().get(0).getDefaultMessage());
			return REDIRECT_PORTFOLIO_INDEX;
		}
		
		LocalDateTime agora = LocalDateTime.now();
		java.sql.Timestamp sqlDate = Timestamp.valueOf(agora);
		p.setData(sqlDate);
		try {
			pDAO.editar(p);
		} catch (SQLException e) {
			redirect.addFlashAttribute("erro", "Impossivel salvar postagem nesse momento.");
			logger.error("persistir postagem : " + e.getMessage());
			return REDIRECT_PORTFOLIO_INDEX;
		}
		
		return REDIRECT_PORTFOLIO_INDEX;
	}
	
}
