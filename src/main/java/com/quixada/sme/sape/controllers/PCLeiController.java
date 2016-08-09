package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

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
import com.quixada.sme.dao.RegionalDAO;
import com.quixada.sme.model.Escola;
import com.quixada.sme.model.Professor;
import com.quixada.sme.model.Regional;
import com.quixada.sme.model.Usuario;


@Controller
public class PCLeiController {
	private static String USUARIO = "usuario";
	private static String ESCOLA = "escola";
	private static String PROFESSOR = "professor";
	private static String REGIONAL = "regional";


	

	
	@RequestMapping(value = {"PCLei/index","/PCLei"})
	public String pcleiIndex(HttpServletRequest request){
		HttpSession session = request.getSession();
		EscolaDAO dao = new EscolaDAO();
		ProfessorDAO PDao = new ProfessorDAO();
	
		/* Sem sessão, manda pro login */
		if (session.getAttribute(USUARIO) == null) {
			return "redirect:../login";
		}
		Usuario usr = (Usuario)session.getAttribute(USUARIO);
	
		if (usr.getIsProfCoordenadorLei()==0) {
			return "redirect:../login";
		}
		
		try {
				Professor pcLei = PDao.busca(usr.getIdUsuario());
				ArrayList<Escola> escolas = dao.getEscolasRegional(pcLei.getIdRegional());
				RegionalDAO rDAO = new RegionalDAO();
				Regional regional = rDAO.buscar(pcLei.getIdRegional());
				if(escolas.isEmpty())
					session.setAttribute("erroGetEscolas", true);
				else {
					session.setAttribute("erroGetEscolas", false);
				}
				session.setAttribute("ArrayEscolas", escolas);
				System.err.println("nome Regional=" + regional.getNome());
				System.err.println("id Regional=" + regional.getIdRegional());

				session.setAttribute(PROFESSOR, pcLei );
				session.setAttribute(REGIONAL, regional );
		
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
		/*verificar se a escola*/
		if (bindingResult.hasErrors()) {
			System.out.println("ERROS\n" + bindingResult);
		}
		if (escola==null) {
			return "redirect:/PCLei/index";
		}
		EscolaDAO eDAO = new EscolaDAO();
		try {
			HttpSession session = request.getSession();
			Regional Regional = (com.quixada.sme.model.Regional) session.getAttribute(REGIONAL);
			escola.setIdRegional(Regional.getIdRegional());
			eDAO.addEscola(escola);
			session.setAttribute("erroAddSchool", "false");
		} catch (SQLException e) {
			e.printStackTrace();
			request.getSession().setAttribute("erroAddSchool", "true");
			return "redirect:/PCLei/index";
		}
		return "redirect:/PCLei/index";
	}
	@RequestMapping(value={"PCLei/rmSchool","rmSchool"})
	public String removerUser(HttpServletRequest request){
		if(request.getParameter("escola")!= null){
			int id = Integer.parseInt( request.getParameter("escola"));
			//System.err.println(id);
			EscolaDAO dao = new EscolaDAO();//
			try {
				dao.excluir(id);
				request.getSession().setAttribute("erroRmSchool", "false");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getSession().setAttribute("erroRmSchool", "true");
			}
		}
		
		return "redirect:/PCLei/index";
	}

}
