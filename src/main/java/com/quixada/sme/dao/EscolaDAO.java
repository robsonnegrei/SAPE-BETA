package com.quixada.sme.dao;

import java.util.ArrayList;

import com.quixada.sme.factory.ConnectionFactory;
import com.quixada.sme.model.Escola;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class EscolaDAO {
	private Connection conexao; 
	
	public ArrayList<Escola> getEscolas() throws SQLException{
		String sql = "SELECT * FROM escola";
		this.conexao = ConnectionFactory.getMySqlConnection();
		PreparedStatement stm = conexao.prepareStatement(sql);
		ArrayList<Escola> escolas = new ArrayList<>();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()){
			Escola e = new Escola(rs.getInt("idEscola"),rs.getInt("idRegional") ,rs.getString("nome"));
			escolas.add(e);
		}
		return escolas;
	}
	public void addEscola( Escola escola) throws SQLException{
		this.conexao = ConnectionFactory.getMySqlConnection();
		String sql = "INSERT INTO escola"
				+ "(idEscola, idReginal, nome) values (?, ?, ?) " ;
		PreparedStatement stm = conexao.prepareStatement(sql);
		stm.setInt(1, escola.getIdEscola());
		stm.setInt(2, escola.getIdRegional());
		stm.setString(3, escola.getNome());
		stm.execute();
	}
	public void editar(Escola escola) throws SQLException{
		this.conexao = ConnectionFactory.getMySqlConnection();
		String sql = "UPDATE escola "
				+ "SET idEscola=?, idRegional=?, nome=? "
				+ "WHERE idEscola=" + escola.getIdEscola();
		
		PreparedStatement stmt =  conexao.prepareStatement(sql);
		stmt.setInt(1, escola.getIdEscola());
		stmt.setInt(2, escola.getIdRegional());
		stmt.setString(3, escola.getNome());
		stmt.execute();
	}
	public void excluir(int idEscola) throws SQLException{
		this.conexao = ConnectionFactory.getMySqlConnection();
		String sql = "DELETE FROM escola WHERE idEscola="+ idEscola;
		PreparedStatement stmt =  conexao.prepareStatement(sql);
		stmt.execute();
	}
	public ArrayList<Escola> getEscolasRegional(int idRegional) throws SQLException{
		String sql = "SELECT * FROM escola WHERE idRegional="+idRegional;
		this.conexao = ConnectionFactory.getMySqlConnection();
		PreparedStatement stm = conexao.prepareStatement(sql);
		//stm.setInt(1, idRegional);
		ArrayList<Escola> escolas = new ArrayList<>();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()){
			Escola e = new Escola(rs.getInt("idEscola"),rs.getInt("idRegional") ,rs.getString("nome"));
			escolas.add(e);
		}
		return escolas;
	}

}
