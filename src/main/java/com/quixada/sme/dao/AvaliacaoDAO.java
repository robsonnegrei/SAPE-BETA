package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.quixada.sme.factory.ConnectionFactory;
import com.quixada.sme.model.Avaliacao;

public class AvaliacaoDAO {

	public void adiciona(Avaliacao aval) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "INSERT INTO avaliacao "
				+ "( ano, data, periodo, idAluno, nome_aluno) "
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setDate(1, aval.getAno());
		stmt.setDate(2, aval.getData());
		stmt.setInt(3, aval.getPeriodo());
		stmt.setInt(4, aval.getIdAluno());
		stmt.setString(5, aval.getNomeAluno());
		stmt.execute();
	}
	
	public Avaliacao buscar(int id) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "SELECT * FROM avaliacao WHERE idAvaliacao="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Avaliacao aval = null;
		if (rs.next()) {
			aval = new Avaliacao();
			aval.setIdAvaliacao(rs.getInt(1));
			aval.setAno(rs.getDate(2));
			aval.setData(rs.getDate(3));
			aval.setPeriodo(rs.getInt(4));
			aval.setIdAluno(rs.getInt(5));
			aval.setNomeAluno(rs.getString(6));
		}
		return aval;
	}

	public void editar(Avaliacao aval) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "UPDATE avaliacao "
				+ "SET ano=?, data=?, periodo=?, idAluno=? "
				+ "WHERE idAvaliacao=" + aval.getIdAvaliacao();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setDate(1, aval.getAno());
		stmt.setDate(2, aval.getData());
		stmt.setInt(3, aval.getPeriodo());
		stmt.setInt(4, aval.getIdAluno());
		stmt.setString(5, aval.getNomeAluno());
		stmt.execute();
	}
	
	public void excluir(int id) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "DELETE FROM avaliacao WHERE idAvaliacao="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
	}

}
