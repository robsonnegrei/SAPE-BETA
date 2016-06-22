package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quixada.sme.dao.AlunoDAO;
import com.quixada.sme.model.Aluno;
import com.quixada.sme.model.Usuario;

@Controller
public class AlunoController {
	private static String USUARIO = "usuario";

@RequestMapping(value ={"/PCLei/getAlunos", "getAlunos"})
	public String listarAlunos(HttpServletRequest request, @RequestParam("idEscola")String idEscola){
	
	HttpSession session = request.getSession();
	AlunoDAO alunoDAO = new AlunoDAO();
	/* Sem sessão, manda pro login */
	if (session.getAttribute(USUARIO) == null) {
		return "redirect:../login";
	}
	Usuario usr = (Usuario)session.getAttribute(USUARIO);

	if (usr.getIsProfCoordenadorLei()==0) {
		return "redirect:../login";
	}
	
	try {
			int id = Integer.parseInt(idEscola);
			ArrayList<Aluno> alunos = alunoDAO.buscarAlunosPorEscola(id);
			

			if(alunos.isEmpty()){
				session.setAttribute("erroGetAlunos", true);
			}else {
				session.setAttribute("erroGetAlunos", false);
			}
			session.setAttribute("ArrayAlunos", alunos);
	
	} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			session.setAttribute("erroGetAlunos", true);
	}
	
	return "PCLei/pagAlunos";
	}

@RequestMapping("/addAluno")
	public String AddAlunos(){
		return null;
	}

@RequestMapping("/rmAluno")
	public String excluirAlunos(){
		return null;
	}
}
