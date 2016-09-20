package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quixada.sme.dao.EscolaDAO;
import com.quixada.sme.dao.PCleiDAO;
import com.quixada.sme.dao.RegionalDAO;
import com.quixada.sme.model.Escola;
import com.quixada.sme.model.PClei;
import com.quixada.sme.model.Regional;
import com.quixada.sme.model.Usuario;


@Controller
public class PCLeiController {
	
	private static Logger logger = LoggerFactory.getLogger(PCLeiController.class);
	
	private final static String PAG_PCLEI_INDEX = "pclei/index";
	private final static String PAG_ADMIN_INDEX = "admin/index";
	private final static String PAG_PCLEI_NEWSCHOOL = "pclei/formSchool";
	private final static String PAG_PCLEI_INDEX_RED = "redirect:/pclei/index";
	
	private static String USUARIO = "usuario";
	private static String ESCOLA = "escola";
	private static String PROFESSOR = "professor";
	private static String REGIONAL = "regional";

	@Autowired
	private EscolaDAO esDAO;
	@Autowired
	private RegionalDAO reDAO;
	@Autowired
	private PCleiDAO pDAO;

	@RequestMapping(value = {"pclei/index","/pclei"})
	public String pcleiIndex(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		Usuario usr = (Usuario)session.getAttribute("usuario");
		try {
				PClei pcLei = pDAO.buscaPorIdUsuario(usr.getIdUsuario());
				if (pcLei == null) {
					model.addAttribute("erro", "Sua conta não possui dados de professor. Adicione esses dados para poder acessar esse conteúdo.");
					return PAG_ADMIN_INDEX;
				}
				ArrayList<Escola> escolas = esDAO.getEscolasRegional(pcLei.getIdRegional());
				Regional regional = reDAO.buscar(pcLei.getIdRegional());
				if(escolas.isEmpty())
					session.setAttribute("erroGetEscolas", true);
				else {
					session.setAttribute("erroGetEscolas", false);
				}
				session.setAttribute("ArrayEscolas", escolas);
				logger.info("Regional : " + regional.getIdRegional() + " " + regional.getNome());

				session.setAttribute(PROFESSOR, pcLei );
				session.setAttribute(REGIONAL, regional );
		
		} catch (NullPointerException | SQLException e) {
				logger.error("pclei : " + e.getMessage());
				e.printStackTrace();
				session.setAttribute("erroGetEscolas", true);
		}
		
		return PAG_PCLEI_INDEX;
	}
	@RequestMapping(value = {"pclei/newSchoolForm"})
	public String newSchoolForm(Model model, HttpServletRequest request)
	{	
		/*criando escola*/
		Escola escola = new Escola();
		model.addAttribute(ESCOLA, escola);
		
		return PAG_PCLEI_NEWSCHOOL;
	}
	@RequestMapping(value = {"pclei/addSchool"}, method = RequestMethod.POST)
	public String addUser(@ModelAttribute(value="escola") Escola escola, BindingResult bindingResult, HttpServletRequest request){
		if (bindingResult.hasErrors()) {
			System.out.println("ERROS\n" + bindingResult);
		}
		HttpSession session = request.getSession();
		Usuario usr = (Usuario)session.getAttribute("usuario");
		logger.info("Req nova escola : " + usr.getEmail());
		if (escola==null) {
			return PAG_PCLEI_INDEX_RED;
		}
		try {
			
			Regional Regional = (com.quixada.sme.model.Regional) session.getAttribute(REGIONAL);
			escola.setIdRegional(Regional.getIdRegional());
			esDAO.addEscola(escola);
			session.setAttribute("erroAddSchool", "false");
		} catch (SQLException e) {
			logger.error("Adicionar escola : " + e.getMessage());
			request.getSession().setAttribute("erroAddSchool", "true");
			return PAG_PCLEI_INDEX_RED;
		}
		return PAG_PCLEI_INDEX_RED;
	}
	@RequestMapping(value={"pclei/rmSchool","rmSchool"})
	public String removerEscola(HttpServletRequest request, HttpSession session){
		if(request.getParameter("escola")!= null){
			Usuario usr = (Usuario)session.getAttribute("usuario");
			int id = Integer.parseInt( request.getParameter("escola"));
			logger.info("Apagar escola : Solicitante - " + usr.getEmail() + " Alvo " + id);
			//System.err.println(id);
			try {
				esDAO.excluir(id);
				request.getSession().setAttribute("erroRmSchool", "false");

			} catch (SQLException e) {
				logger.error("Remover escola : " + e.getMessage());
				request.getSession().setAttribute("erroRmSchool", "true");
			}
		}
		
		return PAG_PCLEI_INDEX_RED;
	}

}
