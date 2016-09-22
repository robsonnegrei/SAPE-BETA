package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.quixada.sme.model.Aluno;



@Component
@ComponentScan(value={"com.quixada.sme.sape.config"})
public class AlunoDAO {
	
	@Autowired
	DataSource ds;

	
	public void adiciona(Aluno aluno) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "INSERT INTO aluno "
				+ "( idEscola, nome) "
				+ "VALUES ( ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, aluno.getIdEscola());
		stmt.setString(2, aluno.getNome());
		//stmt.setString(3, aluno.getNivel());
		stmt.execute();
	}
	
	/*
	 * Atualiza o nivel do aluno de acordo com seu ID
	 * 
	 */
	public void atualizarNivel(Aluno aluno) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "UPDATE aluno "
				+ "SET nivelAtual=?"
				+ "WHERE idAluno=" + aluno.getIdAluno();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, aluno.getNivel());
		stmt.execute();
	}
	
	public Aluno buscar(int id) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "SELECT * FROM aluno WHERE idAluno="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Aluno aluno = null;
		if (rs.next()) {
			aluno = new Aluno();
			aluno.setIdAluno(rs.getInt(1));
			aluno.setIdEscola(rs.getInt(2));
			aluno.setNome(rs.getString(3));
			aluno.setNivel(rs.getString(4));
		}
		return aluno;
	}

	public void editar(Aluno aluno) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "UPDATE aluno "
				+ "SET idEscola=?, nome=?, nivelAtual=?"
				+ "WHERE idAluno=" + aluno.getIdAluno();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, aluno.getIdEscola());
		stmt.setString(2, aluno.getNome());
		stmt.setString(3, aluno.getNivel());
		stmt.execute();
	}
	
	public void excluir(int id) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "DELETE FROM aluno WHERE idAluno="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
	}
	
	public ArrayList<Aluno> buscarAlunosPorEscola(int idEscola) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "SELECT * FROM aluno WHERE idEscola="+ idEscola;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Aluno> alunos = new ArrayList<>();
		while(rs.next()){
			Aluno a = new Aluno(rs.getInt("idAluno"),rs.getInt("idEscola") ,rs.getString("nome"), rs.getString("nivelAtual"));
			alunos.add(a);
		}

		return alunos;
		
	}
}
