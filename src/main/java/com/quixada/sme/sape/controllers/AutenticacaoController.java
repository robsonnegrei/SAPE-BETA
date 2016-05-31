package com.quixada.sme.sape.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quixada.sme.dao.UsuarioDAO;
import com.quixada.sme.model.Usuario;

@Controller
public class AutenticacaoController {
	@RequestMapping("/autenticar")
	public String autenticar(HttpServletRequest request){
		UsuarioDAO dao = new UsuarioDAO();
		try {
			Usuario usr = dao.buscar(request.getParameter("username"));
			if (usr == null || usr.getSenha().equals(request.getParameter("password"))) {
				return "login";
			}
			else{
				return "/";
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return "";
		}
	}
}
