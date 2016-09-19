package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quixada.sme.dao.PCleiDAO;
import com.quixada.sme.dao.RegionalDAO;
import com.quixada.sme.dao.UsuarioDAO;
import com.quixada.sme.model.PClei;
import com.quixada.sme.model.Regional;
import com.quixada.sme.model.Usuario;


@Controller
public class AdminController {
	
	private static Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private UsuarioDAO uDAO;
	@Autowired
	private RegionalDAO reDAO;
	
	@Autowired private PCleiDAO pcDAO;
	
	@RequestMapping(value = {"/admin/index","/admin"})
	public String adminIndex(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		//** Codigo que seta usuario MOVIDO PARA com.quixada.sme.sape.config.CustomAuthenticationSuccessHandler
		
			try {
				List<Usuario> usrList = uDAO.listarTodos();
				session.setAttribute("listaUsuarios", usrList);
			} catch (SQLException e) {
				logger.error("Erro index Admin : " + e.getMessage());
			}
		return "admin/index";
	}

	@RequestMapping(value = {"admin/newUserForm"})
	public String newUserForm(Model model, HttpServletRequest request)
	{
		//HttpSession session = request.getSession();
		//Security redireciona pro login se não estiver autenticado
		Usuario usuario = new Usuario();
		PClei pclei = new PClei();
		List<Regional> regionais = null;
		try {
			regionais = reDAO.listar();
		} catch (SQLException e) {
			logger.error("Erro ao carregar form usuario : " + e.getMessage());
		}
		
		model.addAttribute("pclei", pclei);
		model.addAttribute("usuario", usuario);
		model.addAttribute("regionais", regionais);
		return "admin/formUser";
	}
	
	@RequestMapping(value = {"admin/addUser"}, method = RequestMethod.POST)
	public String addUser( @Validated @ModelAttribute(value="usuario") Usuario usuario,
		@ModelAttribute(value="pclei") PClei pclei,
		BindingResult bindingResult, 
		HttpServletRequest request,
		RedirectAttributes redirect){
		//validações antes de salvar
		if (bindingResult.hasErrors()) {
			redirect.addFlashAttribute("erroAdd",bindingResult.getAllErrors().get(0).toString());
			return "redirect:/admin/index";
		}
		if (usuario == null) {
			redirect.addFlashAttribute("erroAdd","Usuario invalido!");
			return "redirect:/admin/index";
		}
		
		String email = usuario.getEmail();
		try {
			if (uDAO.buscar(email) != null) {
				redirect.addFlashAttribute("erroAdd","Esse email ja esta em uso! Tente novamente com outro email.");
				return "redirect:/admin/index";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			redirect.addFlashAttribute("erroAdd",e1.getMessage());
			return "redirect:/admin/index";
		}
		if (usuario.getIsAdmin()!= 1 && usuario.getIsProfCoordenadorLei() !=1 && usuario.getIsProfessor()!=1) {
			redirect.addFlashAttribute("erroAdd","Selecione um papel de usuario!");
			return "redirect:/admin/index";
		}
		//
		try {
			uDAO.adiciona(usuario);
			if (usuario.getIsProfCoordenadorLei()==1) {
				pclei.setIdUsuario(usuario.getIdUsuario());
				pcDAO.adiciona(pclei);
				
			}
			logger.info("Novo usuario cadastrado : " + usuario.getEmail());
		} catch (SQLException e) {
			logger.error("Erro add usuario : " + e.getMessage());
			redirect.addFlashAttribute("erroAdd",e.getMessage());
			return "redirect:/admin/index";
		}
		return "redirect:/admin/index";
	}
	@RequestMapping("admin/rmuser")
	public String removerUser(HttpServletRequest request, @RequestParam("id") int id){
			
		    //int id = Integer.parseInt( request.getParameter("user"));
			
			try {
				
				uDAO.excluir(id);
				logger.info("Usuario removido : " + id);
				request.getSession().setAttribute("erroRmUser", "false");

			} catch (SQLException e) {
				logger.error("Erro excluir usuario : " + e.getMessage());
				request.getSession().setAttribute("erroRmUser", "true");
			}
		
		return "redirect:/admin/index";
	}
}
