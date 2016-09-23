package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.quixada.sme.model.PClei;

@ComponentScan(value={"com.quixada.sme"})
@Component
public class PCleiDAO {
	
	@Autowired
	DataSource ds;

	public void adiciona(PClei professor) throws SQLException {
		Connection conexao = ds.getConnection();

		String INSERT_QUERY = "INSERT INTO pcLei "
				+ " (idRegional, idUsuario, nome) "
				+ "values (?, ?, ?)";

		PreparedStatement statement = conexao.prepareStatement(INSERT_QUERY);
		statement.setInt(1, professor.getIdRegional());
		statement.setInt(2, professor.getIdUsuario());
		statement.setString(3, professor.getNome());
		statement.execute();
		conexao.close();
	}

	public PClei busca(int id) throws SQLException {
		Connection conexao = ds.getConnection();

		String SELECT_QUERY = "SELECT * FROM pcLei WHERE idProfessor=" + id;

		PreparedStatement statement = conexao.prepareStatement(SELECT_QUERY);

		ResultSet rs = statement.executeQuery();

		PClei professor  = null;
		if(rs.next()) {
			professor = new PClei();
			professor.setIdProfessor(rs.getInt(1));
			professor.setIdRegional(rs.getInt(2));
			professor.setIdUsuario(rs.getInt(3));
			professor.setNome(rs.getString(4));
		}
		conexao.close();
		return professor;
		
	}	
	/*
	 * Retorna um pc lei que tenha o id de usuario informado
	 */
	public PClei buscaPorIdUsuario(int idUsuario) throws SQLException {
		Connection conexao = ds.getConnection();

		String SELECT_QUERY = "SELECT * FROM pcLei WHERE idUsuario=" + idUsuario;

		PreparedStatement statement = conexao.prepareStatement(SELECT_QUERY);

		ResultSet rs = statement.executeQuery();

		PClei professor  = null;
		if(rs.next()) {
			professor = new PClei();
			professor.setIdProfessor(rs.getInt(1));
			professor.setIdRegional(rs.getInt(2));
			professor.setIdUsuario(rs.getInt(3));
			professor.setNome(rs.getString(4));
		}
		conexao.close();
		return professor;
	}	

	public void editar (PClei professor) throws SQLException {
		Connection conexao = ds.getConnection();
		String UPDATE_QUERY = "UPDATE pcLei"
				+ "SET idRegional=?, idUsuario=?, nome=?" 
				+ "WHERE idProfessor=" + professor.getIdProfessor();

		PreparedStatement statement = conexao.prepareStatement(UPDATE_QUERY);
		statement.setInt(1, professor.getIdRegional());
		statement.setInt(2, professor.getIdUsuario());
		statement.setString(3, professor.getNome());
		statement.execute();
		conexao.close();
	}

	public void excluir (int id) throws SQLException {
		Connection conexao = ds.getConnection();

		String DELETE_QUERY = "DELETE FROM pcLei WHERE idProfessor=" + id;
		PreparedStatement statement = conexao.prepareStatement(DELETE_QUERY);
		statement.execute();
		conexao.close();
	}
}