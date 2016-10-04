package com.quixada.sme.sape.controllers;

import com.quixada.sme.dao.PCleiDAO;
import com.quixada.sme.dao.RegionalDAO;
import com.quixada.sme.dao.UsuarioDAO;
import com.quixada.sme.model.Configuracao;
import com.quixada.sme.model.PClei;
import com.quixada.sme.model.Regional;
import com.quixada.sme.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;


@Controller
public class AdminController {
	
	private static Logger logger = LoggerFactory.getLogger(AdminController.class);

	private static final String ADMIN_INDEX = "admin/index";
    private static final String ADMIN_INDEX_REDIR = "redirect:/admin/index";
    private static final String ADMIN_FRM_USER = "admin/formUser";
    private static final String ADMIN_CONFIG = "admin/config";

	@SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
	private UsuarioDAO uDAO;
	@SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
	private RegionalDAO reDAO;

	@SuppressWarnings("SpringJavaAutowiringInspection")
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
		return ADMIN_INDEX;
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
		return ADMIN_FRM_USER;
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
			return ADMIN_INDEX_REDIR;
		}
		if (usuario == null) {
			redirect.addFlashAttribute("erroAdd","Usuario invalido!");
			return ADMIN_INDEX_REDIR;
		}
		
		String email = usuario.getEmail();
		try {
			if (uDAO.buscar(email) != null) {
				redirect.addFlashAttribute("erroAdd","Esse email ja esta em uso! Tente novamente com outro email.");
				return ADMIN_INDEX_REDIR;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			redirect.addFlashAttribute("erroAdd",e1.getMessage());
			return ADMIN_INDEX_REDIR;
		}
		if (usuario.getIsAdmin()!= 1 && usuario.getIsProfCoordenadorLei() !=1 && usuario.getIsProfessor()!=1) {
			redirect.addFlashAttribute("erroAdd","Selecione um papel de usuario!");
			return ADMIN_INDEX_REDIR;
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
			return ADMIN_INDEX_REDIR;
		}
		return ADMIN_INDEX_REDIR;
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
		
		return ADMIN_INDEX_REDIR;
	}

	@RequestMapping(value = "admin/cfg", method = RequestMethod.GET)
	public String config(Model model, HttpServletRequest request){
		try {
            //carregar configs
			model.addAttribute("anoAval", Configuracao.ANO_AVALIACAO_ATUAL);
		} catch (Exception e) {
			logger.error("Erro carregar configuracoes : " + e.getMessage());
		}

		return ADMIN_CONFIG;
	}
}
