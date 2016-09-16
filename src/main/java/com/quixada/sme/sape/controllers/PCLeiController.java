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
	public String pcleiIndex(HttpServletRequest request){
		HttpSession session = request.getSession();
		Usuario usr = (Usuario)session.getAttribute("usuario");
		try {
				PClei pcLei = pDAO.buscaPorIdUsuario(usr.getIdUsuario());
				ArrayList<Escola> escolas = esDAO.getEscolasRegional(pcLei.getIdRegional());
				Regional regional = reDAO.buscar(pcLei.getIdRegional());
				if(escolas.isEmpty())
					session.setAttribute("erroGetEscolas", true);
				else {
					session.setAttribute("erroGetEscolas", false);
				}
				session.setAttribute("ArrayEscolas", escolas);
				logger.info("listar escolas : " + regional.getIdRegional() + " " + regional.getNome());

				session.setAttribute(PROFESSOR, pcLei );
				session.setAttribute(REGIONAL, regional );
		
		} catch (SQLException e) {
				logger.error("pclei : " + e.getMessage());
				e.printStackTrace();
				session.setAttribute("erroGetEscolas", true);
		}
		
		return "PCLei/index";
	}
	@RequestMapping(value = {"PCLei/newSchoolForm"})
	public String newSchoolForm(Model model, HttpServletRequest request)
	{	
		HttpSession session = request.getSession();
		
		//Sem sessão, manda pro login
		if (session.getAttribute(USUARIO) == null) {
			return "redirect:../login";
		}
		Usuario usr = (Usuario)session.getAttribute(USUARIO);
		if (usr.getIsProfCoordenadorLei()==0) {
			return "redirect:../login";
		}
		/*criando escola*/
		Escola escola = new Escola();
		model.addAttribute(ESCOLA, escola);
		
		return "PCLei/formSchool";
	}
	@RequestMapping(value = {"PCLei/addSchool"}, method = RequestMethod.POST)
	public String addUser(@ModelAttribute(value="escola") Escola escola, BindingResult bindingResult, HttpServletRequest request){
		if (bindingResult.hasErrors()) {
			System.out.println("ERROS\n" + bindingResult);
		}
		HttpSession session = request.getSession();
		Usuario usr = (Usuario)session.getAttribute("usuario");
		logger.info("Req nova escola : " + usr.getEmail());
		if (escola==null) {
			return "redirect:/pclei/index";
		}
		try {
			
			Regional Regional = (com.quixada.sme.model.Regional) session.getAttribute(REGIONAL);
			escola.setIdRegional(Regional.getIdRegional());
			esDAO.addEscola(escola);
			session.setAttribute("erroAddSchool", "false");
		} catch (SQLException e) {
			logger.error("Adicionar escola : " + e.getMessage());
			request.getSession().setAttribute("erroAddSchool", "true");
			return "redirect:/PCLei/index";
		}
		return "redirect:/pclei/index";
	}
	@RequestMapping(value={"PCLei/rmSchool","rmSchool"})
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
		
		return "redirect:/pclei/index";
	}

}
