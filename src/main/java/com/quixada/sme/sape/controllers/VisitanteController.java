package com.quixada.sme.sape.controllers;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
	private HttpSession session = null;
	private static int ID_VISITANTE = -1;
	
	
	@RequestMapping("/escolas")
	public String getEscolasRegional(HttpSession session, @RequestParam("idRegional")String idRegional, @RequestParam("nome")String nome){
		EscolaDAO dao = new EscolaDAO(); 
		/*
		Usuario usr = (Usuario) session.getAttribute("usuario");
		if(usr.getIdUsuario() == ID_VISITANTE ){
			this.session = session;
		}else{
			return "redirect:/login";
		}
		*/
		Regional regional = new Regional(Integer.parseInt(idRegional), nome);
	
		try {
			ArrayList<Escola> escolas = dao.getEscolasRegional(regional.getIdRegional());
			if(escolas.isEmpty())
				session.setAttribute("erroGetEscolas", true);
			else {
				session.setAttribute("erroGetEscolas", false);
			}
			session.setAttribute("ArrayEscolas", escolas);
	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			session.setAttribute("erroGetEscolas", true);
		}
		
		
		session.setAttribute("regional", regional);
		return "redirect:/home";
	}
	
	@RequestMapping(value ={"/visitar"}, method = RequestMethod.POST)
	public String autenticarVisitante(HttpSession session){
		//Identificação do visitante
		SecureRandom random = new SecureRandom();
	    byte bytes[] = new byte[8];
	    random.nextBytes(bytes);
	    String token = bytes.toString();
	    //Autoridade
	  
	    List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
	    GrantedAuthority ga = new SimpleGrantedAuthority("VISITANTE");
	    grantedAuthorities.add(ga);
	    //cria o visitante - Spring Security safe
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("visitante"+token+"@sape", "default",grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		//usuario da sessio
		Usuario usuarioAutenticado = new Usuario();
		usuarioAutenticado.setEmail("visitante"+token+"@sape");
		usuarioAutenticado.setIdUsuario(-1);
		usuarioAutenticado.setSenha("default");
		session.setMaxInactiveInterval(1200); //20 minutos
		session.setAttribute("idEscola", 1); //remover essa linha
		session.setAttribute("usuario", usuarioAutenticado);
		
		return "/home";
	}
}