package com.quixada.sme.sape.controllers;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.quixada.sme.dao.EscolaDAO;
import com.quixada.sme.model.Escola;
import com.quixada.sme.model.Regional;
import com.quixada.sme.model.Usuario;


@Controller
public class VisitanteController {
	
	private static Logger logger = LoggerFactory.getLogger(VisitanteController.class);
	
	@RequestMapping("/escolas")
	public String getEscolasRegional(HttpSession session, @RequestParam("idRegional")String idRegional, @RequestParam("nome")String nome){
		
		EscolaDAO dao = new EscolaDAO(); 
		Regional regional = new Regional(Integer.parseInt(idRegional), nome);
		logger.info("getEscolasRegional executado");
		try {
			ArrayList<Escola> escolas = dao.getEscolasRegional(regional.getIdRegional());
			if(escolas.isEmpty())
				session.setAttribute("erroGetEscolas", true);
			else {
				session.setAttribute("erroGetEscolas", false);
			}
			session.setAttribute("ArrayEscolas", escolas);
	
		} catch (SQLException e) {
			logger.error("Em getEscolasRegional :: " + e.getMessage());;
			//e.printStackTrace();
			session.setAttribute("erroGetEscolas", true);
		}
		
		
		session.setAttribute("regional", regional);
		return "redirect:home";
	}
	
	@RequestMapping(value ={"/visitar"}, method = RequestMethod.POST)
	public String autenticarVisitante(HttpSession session){
		logger.info("Novo visitante");
		//Identificação do visitante
		char[] permitido = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		SecureRandom random = new SecureRandom();
		StringBuilder token = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			token.append(random.nextInt(permitido.length));
		}
	    //Autoridade
	    List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
	    GrantedAuthority ga = new SimpleGrantedAuthority("VISITANTE");
	    grantedAuthorities.add(ga);
	    //cria o visitante - Spring Security safe
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("visitante"+token.toString()+"@sape", "default",grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		//usuario da session
		Usuario usuarioAutenticado = new Usuario();
		usuarioAutenticado.setEmail("visitante"+token.toString()+"@sape");
		usuarioAutenticado.setIdUsuario(-1);
		usuarioAutenticado.setSenha("default");
		session.setMaxInactiveInterval(1200); //20 minutos
		session.setAttribute("idEscola", 1); //remover essa linha
		session.setAttribute("usuario", usuarioAutenticado);
		logger.info("Visitante: " + "visitante"+token+"@sape" + " salvo em memoria. inatividade: 1200 s");

		return "portfolio/index";
	}
	
	@RequestMapping(value ={"/visitar"}, method = RequestMethod.GET)
	public String rederecionaGet(HttpSession session){
		logger.info("Redirecionando visitante");
		return "redirect:login";
	}
}