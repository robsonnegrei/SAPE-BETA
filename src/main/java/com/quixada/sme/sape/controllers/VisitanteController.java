package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		
		Usuario usr = (Usuario) session.getAttribute("usuario");
		if(usr.getIdUsuario() == ID_VISITANTE ){
			this.session = session;
		}else{
			return "redirect:/login";
		}
		
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
}