package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quixada.sme.dao.EscolaDAO;
import com.quixada.sme.dao.ProfessorDAO;
import com.quixada.sme.dao.UsuarioDAO;
import com.quixada.sme.model.Escola;
import com.quixada.sme.model.Professor;
import com.quixada.sme.model.Usuario;

@Controller
public class PCLeiController {
	@RequestMapping(value = {"PCLei/index","/admin"})
	public String adminIndex(HttpServletRequest request){
		HttpSession session = request.getSession();
		EscolaDAO dao = new EscolaDAO();
		ProfessorDAO PDao = new ProfessorDAO();
		
	
		//Sem sessão, manda pro login
		if (session.getAttribute("usuario") == null) {
			return "redirect:../login";
		}
		Usuario usr = (Usuario)session.getAttribute("usuario");
	
		if (usr.getIsProfCoordenadorLei()==0) {
			return "redirect:../login";
		}
		try {
				Professor pcLei = PDao.busca(usr.getIdUsuario());
				ArrayList<Escola> escolas = dao.getEscolasRegional(pcLei.getIdRegional());
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
		
		return "PCLei/index";
	}
	@RequestMapping(value = {"PCLei/newSchoolForm"})
	public String newUserForm(Model model, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		
		//Sem sessão, manda pro login
		if (session.getAttribute("usuario") == null) {
			return "redirect:../login";
		}
		Usuario usr = (Usuario)session.getAttribute("usuario");
		if (usr.getIsAdmin()==0) {
			return "redirect:../login";
		}
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "admin/formSchool";
	}
	@RequestMapping(value = {"admin/addSchool"}, method = RequestMethod.POST)
	public String addUser(@ModelAttribute(value="usuario") Usuario usuario, 
		BindingResult bindingResult, 
		HttpServletRequest request){
		//verificar se o usuario esta vindo
		if (bindingResult.hasErrors()) {
			System.out.println("ERROS\n" + bindingResult);
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
	@RequestMapping("admin/rmSchool")
	public String removerUser(HttpServletRequest request){
		if(request.getParameter("user")!= null){
			int id = Integer.parseInt( request.getParameter("user"));
			//System.err.println(id);
			UsuarioDAO dao = new UsuarioDAO();
			try {
				dao.excluir(id);
				request.getSession().setAttribute("erroRmSchool", "false");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getSession().setAttribute("erroRmSchool", "true");
			}
		}
		
		return "redirect:/admin/index";
	}
}
