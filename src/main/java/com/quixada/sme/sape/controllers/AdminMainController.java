package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quixada.sme.dao.UsuarioDAO;
import com.quixada.sme.model.Usuario;


@Controller
public class AdminMainController {

	@RequestMapping("admin/index")
	public String adminIndex(HttpServletRequest request){
		HttpSession session = request.getSession();
	
		//Sem sessão, manda pro login
		if (session.getAttribute("usuario") == null) {
			return "redirect:../login";
		}
		Usuario usr = (Usuario)session.getAttribute("usuario");
		if (usr.getIsAdmin()==0) {
			return "redirect:../login";
		}
		//Retorna lista de usuarios, pode ser subtituido por AJAX posteriormente
		UsuarioDAO dao = new UsuarioDAO();
		try {
			List<Usuario> usrList = dao.listarTodos();
			session.setAttribute("listaUsuarios", usrList);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return "admin/index";
	}
}
