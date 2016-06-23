package com.quixada.sme.sape.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quixada.sme.dao.UsuarioDAO;
import com.quixada.sme.model.Usuario;

@Controller
public class AutenticacaoController {
	HttpSession session;
	private static String EMAIL_VISITANTE = "visitante@sape.com";
	private static String SENHA_VISITANTE = "";
	private static int ID_VISITANTE = -1;
	

//	@RequestMapping(value = "/autenticar", method = RequestMethod.POST)
//	public String autenticar(HttpServletRequest request){
//		UsuarioDAO dao = new UsuarioDAO();
//		if(request.getSession()!= null){
//			session = request.getSession();
//		}
//		
//		if(request.getParameter("username").equals(EMAIL_VISITANTE)){
//			// criando usuario visitante
//			Usuario visitante = new Usuario(ID_VISITANTE, EMAIL_VISITANTE, SENHA_VISITANTE, 0, 0,0);
//			session.setAttribute("usuario", visitante);
//			return "redirect:/home";
//		}else{
//			try {
//				Usuario usr = dao.buscar(request.getParameter("username"));
//				if (usr != null && usr.getSenha().equals(request.getParameter("password"))) {
//					session.setAttribute("usuario", usr);
//					if(usr.getIsAdmin()== 1)	
//							return "redirect:/admin/index";
//					else if(usr.getIsProfCoordenadorLei()==1)
//							return "redirect:/PCLei/index";
//				}else{
//					return "redirect:/login?error";
//				}
//			} catch (SQLException e) {
//				
//				e.printStackTrace();
//				return "redirect:/login?error";
//			}
//		}
//		return "redirect:/login?error";
//	}
//	@RequestMapping("/logout")
//	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session){
//		session.invalidate(); 
//		return "redirect:/login?logout";
//	}
}
