package com.quixada.sme.sape.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quixada.sme.dao.UsuarioDAO;
import com.quixada.sme.model.Usuario;

@Controller
public class AutenticacaoController {
	HttpSession session;

	@RequestMapping(value = "/autenticar", method = RequestMethod.POST)
	public String autenticar(HttpServletRequest request){
		UsuarioDAO dao = new UsuarioDAO();
		if(request.getSession()!= null){
			session = request.getSession();
		}
		try {
			Usuario usr = dao.buscar(request.getParameter("username"));
			if (usr == null || usr.getSenha().equals(request.getParameter("password"))) {
				session.setAttribute("usuario", usr);
				if(usr.getIsAdmin()== 1)	
						return "redirect:/admin/index";
				else if(usr.getIsProfCoordenadorLei()==1)
						return "redirect:/PCLei/index";
				
				return "redirect:/index";
			}
			else{
				return "redirect:/login";
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return "redirect:/login";
		}
	}
}
