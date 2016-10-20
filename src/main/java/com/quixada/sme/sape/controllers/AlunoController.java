package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.quixada.sme.model.Configuracao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.quixada.sme.dao.AlunoDAO;
import com.quixada.sme.dao.AvaliacaoDAO;
import com.quixada.sme.model.Aluno;
import com.quixada.sme.model.AlunosAvalForm;
import com.quixada.sme.model.Avaliacao;

@Controller
public class AlunoController {
	
	private static Logger logger = LoggerFactory.getLogger(AlunoController.class);
	
	private static final String PCLEI_GET_ALUNOS = "pclei/pagAlunos";
	private static final String PCLEI_AVALIAR_ALUNOS = "pclei/pagAvaliarAlunos";
	private static final String PCLEI_GET_ALUNOS_ESCOLA = "redirect:/pclei/getAlunos?idEscola=";
	
	@Autowired 
	private AlunoDAO aDAO;
	@Autowired 
	private AvaliacaoDAO avalDAO;
	
	@RequestMapping(value ={"pclei/getAlunos", "getAlunos"})
	@PreAuthorize("hasAnyRole('ADMIN','PCLEI')")
	public String listarAlunos(Model model, @RequestParam("idEscola")String idEscola, HttpSession session){
	
	try {
			int id = Integer.parseInt(idEscola);
			ArrayList<Aluno> alunos = aDAO.buscarAlunosPorEscola(id);
			session.setAttribute("idEscola", id);
			
			if(alunos.isEmpty()){
				model.addAttribute("erroGetAlunos", true);
				//session.setAttribute("erroGetAlunos", true);
			}else {
				model.addAttribute("erroGetAlunos", false);
				
				//session.setAttribute("erroGetAlunos", false);
			}
			
			model.addAttribute("ArrayAlunos", alunos);
			//session.setAttribute("ArrayAlunos", alunos);
	
	} catch (SQLException e) {
			logger.error("Listar alunos : " + e.getMessage());
			model.addAttribute("erroGetAlunos", true);
			//session.setAttribute("erroGetAlunos", true);
	}
	
	return PCLEI_GET_ALUNOS;
	}

	@RequestMapping(value={"pclei/addAluno","/addAluno"})
	public String AddAlunos(HttpServletRequest request){
	if(request.getParameter("nome")!= null){
		int idEscola = Integer.parseInt(request.getParameter("idEscola"));
		String nome = request.getParameter("nome");
		int idAluno= 0;
		Aluno aluno = new Aluno(idAluno,idEscola,nome,"");
		
		try {
			aDAO.adiciona(aluno);
			request.getSession().setAttribute("erroAddAluno", "false");
		} catch (SQLException e) {
			logger.error("Add aluno : " + e.getMessage());
			request.getSession().setAttribute("erroAddAluno", "true");
		}
		
	}
	int idEscola = (int) request.getSession().getAttribute("idEscola");
	return PCLEI_GET_ALUNOS_ESCOLA+idEscola;
	}

	@RequestMapping(value={"pclei/rmAluno","/rmAluno"})
	public String excluirAlunos(HttpServletRequest request){
		if(request.getParameter("aluno")!= null){
			int id = Integer.parseInt(request.getParameter("aluno"));
			//System.err.println(id);
			try {
				aDAO.excluir(id);
				request.getSession().setAttribute("erroRmAluno", "false");
	
			} catch (SQLException e) {
				logger.error("Excluir aluno : " + e.getMessage());
				request.getSession().setAttribute("erroRmAluno", "true");
			}
		}
		int idEscola = (int) request.getSession().getAttribute("idEscola");
		return PCLEI_GET_ALUNOS_ESCOLA+idEscola;
	}
	
	@RequestMapping(value="/pclei/avaliar", method=RequestMethod.GET)
	public String avaliarAlunos(HttpSession session, 
			Model model)
	 {
		
		try{	
			int idEscola = ((int) session.getAttribute("idEscola"));
			AlunosAvalForm alunos = new AlunosAvalForm();
			alunos.setAlunosList(aDAO.buscarAlunosPorEscola(idEscola));
			if(alunos.getAlunosList().isEmpty()){
				return PCLEI_GET_ALUNOS;
			}else{
				model.addAttribute("AvaliacaoHeader", String.valueOf(Configuracao.ANO_AVALIACAO_ATUAL) + "." + String.valueOf(Configuracao.PERIODO_AVALIACAO_ATUAL));
				model.addAttribute("wrapper", alunos);
			}
		}
		catch(SQLException e){
			logger.error("Erro : " + e.getMessage());
		}
	    
		return PCLEI_AVALIAR_ALUNOS;
	}

	@RequestMapping(value="/pclei/avaliar", method=RequestMethod.POST)
	public String processoAvaliacaoAlunos(
		@ModelAttribute AlunosAvalForm alunos,
		Model model,
		BindingResult result,
		HttpSession session){
		
	 
	//Gerar as avaliacoes e salvar
	if(alunos.getAlunosList().isEmpty()){
		return PCLEI_GET_ALUNOS;
	}else{
		for (Aluno aluno : alunos.getAlunosList()) {
			Avaliacao aval = new Avaliacao();
			aval.setAno(Configuracao.ANO_AVALIACAO_ATUAL);
			LocalDateTime agora = LocalDateTime.now();
			java.sql.Timestamp sqlDate = Timestamp.valueOf(agora);
			aval.setData(sqlDate);
			aval.setIdAluno(aluno.getIdAluno());
			aval.setNivel(aluno.getNivel());
			aval.setPeriodo(Configuracao.PERIODO_AVALIACAO_ATUAL); //deve vir das configurações do sistema, pelo amor de shiva
			logger.info(aluno.getNome() + " : avaliado : " + aval.getIdAvaliacao() + " : por : " + SecurityContextHolder.getContext().getAuthentication().getName());
			try {
				avalDAO.adiciona(aval);
				//Atualizar nivel do aluno!
				aDAO.atualizarNivel(aluno);
			} catch (SQLException e) {
				logger.error("Erro ao avaliar alunos : " + e.getMessage());
			}
		}
	}

	int idEscola = (int) session.getAttribute("idEscola");
	return PCLEI_GET_ALUNOS_ESCOLA+idEscola;
}


}