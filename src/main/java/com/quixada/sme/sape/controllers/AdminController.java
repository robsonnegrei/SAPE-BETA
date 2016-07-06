package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quixada.sme.dao.UsuarioDAO;
import com.quixada.sme.model.Usuario;


@Controller
public class AdminController {

	@RequestMapping(value = {"/admin/index","/admin"})
	public String adminIndex(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		//Security ja cria a sessão basta pegar e setar o usuario após o login
		//Deve ser feito nas outras ROLES tambem
		//Retorna lista de usuarios, pode ser subtituido por AJAX posteriormente
		UsuarioDAO dao = new UsuarioDAO();
		try {
			SecurityContext context = SecurityContextHolder.getContext();
			String email = context.getAuthentication().getName(); 
			
			Usuario user = dao.buscar(email);
			session.setAttribute("usuario", user);
			List<Usuario> usrList = dao.listarTodos();
			session.setAttribute("listaUsuarios", usrList);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return "admin/index";
	}

	@RequestMapping(value = {"admin/newUserForm"})
	public String newUserForm(Model model, HttpServletRequest request)
	{
		//HttpSession session = request.getSession();
		//Security redireciona pro login se não estiver autenticado
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "admin/formUser";
	}
	
	@RequestMapping(value = {"admin/addUser"}, method = RequestMethod.POST)
	public String addUser(@ModelAttribute(value="usuario") Usuario usuario, 
		BindingResult bindingResult, 
		HttpServletRequest request){
		//verificar se o usuario esta vindo
		if (bindingResult.hasErrors()) {
			System.err.println("ERROS\n" + bindingResult);
		}
		if (usuario ==null) {
			return "redirect:/admin/index";
		}
		UsuarioDAO uDAO = new UsuarioDAO();
		try {
			uDAO.adiciona(usuario);
		} catch (SQLException e) {
			e.printStackTrace();			
			return "redirect:/admin/index";
		}
		return "redirect:/admin/index";
	}
	@RequestMapping("admin/rmUser")
	public String removerUser(HttpServletRequest request){
		if(request.getParameter("user")!= null){
			int id = Integer.parseInt( request.getParameter("user"));
			//System.err.println(id);
			UsuarioDAO dao = new UsuarioDAO();
			try {
				dao.excluir(id);
				request.getSession().setAttribute("erroRmUser", "false");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getSession().setAttribute("erroRmUser", "true");
			}
		}
		
		return "redirect:/admin/index";
	}
}
