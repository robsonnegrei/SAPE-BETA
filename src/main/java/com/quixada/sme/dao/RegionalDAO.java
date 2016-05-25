package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.quixada.sme.factory.ConnectionFactory;
import com.quixada.sme.model.Regional;


public class RegionalDAO {
	
	public void adiciona(Regional regional) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "INSERT INTO regional "
				+ "(nome) "
				+ "VALUES (?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(2, regional.getNome());
		stmt.execute();
	}
	
	public Regional buscar(int id) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "SELECT * FROM regional WHERE idRegional="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Regional regional = null;
		if (rs.next()) {
			regional = new Regional();
			regional.setIdRegional(rs.getInt(1));			
			regional.setNome(rs.getString(2));
		}
		return regional;
	}

	public void editar(Regional regional) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "UPDATE reginoal "
				+ "SET nome=? "
				+ "WHERE idReginonal=" + regional.getIdRegional();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, regional.getNome());
		stmt.execute();
	}
	
	public void excluir(int id) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "DELETE FROM regional WHERE idRegional="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
	}

}
