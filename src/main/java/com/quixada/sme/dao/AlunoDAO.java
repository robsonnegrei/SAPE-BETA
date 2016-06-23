package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.quixada.sme.factory.ConnectionFactory;
import com.quixada.sme.model.Aluno;

public class AlunoDAO {
	public void adiciona(Aluno aluno) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "INSERT INTO aluno "
				+ "( idEscola, nome) "
				+ "VALUES ( ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, aluno.getIdEscola());
		stmt.setString(2, aluno.getNome());
		stmt.execute();
	}
	
	public Aluno buscar(int id) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "SELECT * FROM aluno WHERE idAluno="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Aluno aluno = null;
		if (rs.next()) {
			aluno = new Aluno();
			aluno.setIdAluno(rs.getInt(1));
			aluno.setIdEscola(rs.getInt(2));
			aluno.setNome(rs.getString(3));
		}
		return aluno;
	}

	public void editar(Aluno aluno) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "UPDATE aluno "
				+ "SET idEscola=?, nome=? "
				+ "WHERE idAluno=" + aluno.getIdAluno();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, aluno.getIdEscola());
		stmt.setString(2, aluno.getNome());
		stmt.execute();
	}
	
	public void excluir(int id) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "DELETE FROM aluno WHERE idAluno="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
	}
	public ArrayList<Aluno> buscarAlunosPorEscola(int idEscola) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "SELECT * FROM aluno WHERE idEscola="+ idEscola;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Aluno> alunos = new ArrayList<>();
		while(rs.next()){
			Aluno a = new Aluno(rs.getInt("idAluno"),rs.getInt("idEscola") ,rs.getString("nome"));
			alunos.add(a);
		}
	
		return alunos;
		
	}
}
