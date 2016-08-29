package com.quixada.sme.sape.controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.quixada.sme.dao.AlunoDAO;
import com.quixada.sme.model.Aluno;
import com.quixada.sme.model.Avaliacao;
import com.quixada.sme.model.Usuario;

@Controller
public class AlunoController {

	private static final String USUARIO = "usuario";
	private static final String PCLEI_GET_ALUNOS = "/PCLei/pagAlunos";

	@Autowired
	private AlunoDAO aDAO;
	
	@RequestMapping(value ={"PCLei/getAlunos", "getAlunos"})
	@PreAuthorize("hasAnyRole('ADMIN','PCLEI')")
	public String listarAlunos(HttpServletRequest request, @RequestParam("idEscola")String idEscola, HttpSession session){
	
	try {
			int id = Integer.parseInt(idEscola);
			ArrayList<Aluno> alunos = aDAO.buscarAlunosPorEscola(id);
			session.setAttribute("idEscola", id);
			
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
	
	return PCLEI_GET_ALUNOS;
	}

@RequestMapping(value={"PCLei/addAluno","/addAluno"})
	public String AddAlunos(HttpServletRequest request){
	if(request.getParameter("nome")!= null){
		int idEscola = Integer.parseInt(request.getParameter("idEscola"));
		String nome = request.getParameter("nome");
		int idAluno= 0;
		AlunoDAO Adao = new AlunoDAO();
		Aluno aluno = new Aluno(idAluno,idEscola,nome,"");
		
		try {
			Adao.adiciona(aluno);
			request.getSession().setAttribute("erroAddAluno", "false");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.getSession().setAttribute("erroAddAluno", "true");
		}
		
	}
	int idEscola = (int) request.getSession().getAttribute("idEscola");
	return "redirect:/PCLei/getAlunos?idEscola="+idEscola;
	}

@RequestMapping(value={"PCLei/rmAluno","/rmAluno"})
	public String excluirAlunos(HttpServletRequest request){
		if(request.getParameter("aluno")!= null){
			int id = Integer.parseInt(request.getParameter("aluno"));
			//System.err.println(id);
			AlunoDAO Adao = new AlunoDAO();
			try {
				Adao.excluir(id);
				request.getSession().setAttribute("erroRmAluno", "false");
	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getSession().setAttribute("erroRmAluno", "true");
			}
		}
		int idEscola = (int) request.getSession().getAttribute("idEscola");
		return "redirect:/PCLei/getAlunos?idEscola="+idEscola;
	}
@RequestMapping(value="/PCLei/avaliar",method = RequestMethod.POST)
	public String avaliarAlunos(HttpServletRequest request){
	
		ArrayList<Aluno> Alunos = (ArrayList<Aluno>) request.getSession().getAttribute("ArrayAlunos");
		if(Alunos.isEmpty()){
			return PCLEI_GET_ALUNOS;
		}else{
			
			 SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
			 Calendar cal = Calendar.getInstance();
			    
			ArrayList<Avaliacao> arrAvaliacao = new ArrayList<>();
			
			for (Aluno aluno : Alunos){                     
				Avaliacao av = new Avaliacao();
				av.setNomeAluno(aluno.getNome());
				av.setIdAluno(aluno.getIdAluno());
				arrAvaliacao.add(av);
			}
			request.getSession().setAttribute("ArrayAvaliacao",arrAvaliacao);
			
			/*java.util.Date data = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.format(data);
			Date date = (Date) data;
			 */
			
		}
		return "/PCLei/pagAvaliarAlunos";
	}
@RequestMapping(value="/PCLei/processarAvaliacao",method = RequestMethod.POST)
public String processoAvaliacaoAlunos(HttpServletRequest request){

	ArrayList<Avaliacao> avaliacoes = (ArrayList<Avaliacao>) request.getSession().getAttribute("ArrayAvaliacao");
	if(avaliacoes.isEmpty()){
		return PCLEI_GET_ALUNOS;
	}else{
		
		for (Avaliacao aluno : avaliacoes){                     
			System.out.println("Nome = " + aluno.getNomeAluno() + "nivel= " + aluno.getNivel());
		}
	}
	return PCLEI_GET_ALUNOS;
}

}
