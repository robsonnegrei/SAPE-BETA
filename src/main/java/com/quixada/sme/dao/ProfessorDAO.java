package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.quixada.sme.factory.ConnectionFactory;
import com.quixada.sme.model.Professor;

public class ProfessorDAO {

	public void adiciona(Professor professor) throws SQLException {
		Connection conexao = ConnectionFactory.getMySqlConnection();

		String INSERT_QUERY = "INSERT INTO professor "
				+ " (idRegional, idUsuario, nome) "
				+ "values (?, ?, ?)";

		PreparedStatement statement = conexao.prepareStatement(INSERT_QUERY);
		statement.setInt(1, professor.getIdRegional());
		statement.setInt(2, professor.getIdUsuario());
		statement.setString(3, professor.getNome());
		statement.execute();
	}

	public Professor busca(int id) throws SQLException {
		Connection conexao = ConnectionFactory.getMySqlConnection();

		String SELECT_QUERY = "SELECT * FROM professor WHERE idUsuario=" + id;

		PreparedStatement statement = conexao.prepareStatement(SELECT_QUERY);

		ResultSet rs = statement.executeQuery();

		Professor professor  = null;
		if(rs.next()) {
			professor = new Professor();
			professor.setIdProfessor(rs.getInt(1));
			professor.setIdRegional(rs.getInt(2));
			professor.setIdUsuario(rs.getInt(3));
			professor.setNome(rs.getString(4));
		}
		return professor;
	}	


	public void editar (Professor professor) throws SQLException {
		Connection conexao = ConnectionFactory.getMySqlConnection();
		String UPDATE_QUERY = "UPDATE professor"
				+ "SET idRegional=?, idUsuario=?, nome=?" 
				+ "WHERE idProfessor=" + professor.getIdProfessor();

		PreparedStatement statement = conexao.prepareStatement(UPDATE_QUERY);
		statement.setInt(1, professor.getIdRegional());
		statement.setInt(2, professor.getIdUsuario());
		statement.setString(3, professor.getNome());
		statement.execute();
	}

	public void excluir (int id) throws SQLException {
		Connection conexao = ConnectionFactory.getMySqlConnection();

		String DELETE_QUERY = "DELETE FROM professor WHERE idProfessor=" + id;
		PreparedStatement statement = conexao.prepareStatement(DELETE_QUERY);
		statement.execute();
	}
}