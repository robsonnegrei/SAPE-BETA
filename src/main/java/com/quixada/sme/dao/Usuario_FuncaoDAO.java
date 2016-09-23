package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quixada.sme.model.Usuario;

@Component
public class Usuario_FuncaoDAO {
	
	@Autowired
	DataSource ds;
	
	public void adicionaFuncao(int id, String funcao) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "INSERT INTO usuario_funcao "
				+ "( idUsuario, funcao) "
				+ "VALUES (?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.setString(2, funcao);
		stmt.execute();
		con.close();
	}
	
	public String getFuncao(int id) throws SQLException {
		Connection con = ds.getConnection();
		String sql = "SELECT funcao FROM usuario_funcao WHERE idUsuario="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			return rs.getString(1);
		}
		con.close();
		return null;
	}
	/**
	 * Preenche os atributos presentes na classe usuario de acordo com a funcao atribuida na base de dados
	 * @param usr
	 * @throws SQLException
	 */
	public void fillFuncao(Usuario usr) throws SQLException {
		String funcao = getFuncao(usr.getIdUsuario());
		if (funcao.equals("ADMIN")) {
			usr.setIsAdmin(1);
		}
		if (funcao.equals("PCLEI")) {
			usr.setIsProfCoordenadorLei(1);
		}
		if (funcao.equals("PROFESSOR")) {
			usr.setIsProfessor(1);
		}
	}
	
	public void removeFuncao(int id, String funcao) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "DELETE FROM usuario_funcao WHERE idUsuario="+ id +" AND funcao='"+funcao+"'";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
		con.close();
	}
	/*
	 * Remove as funcoes do usuario
	 */
	public void limparFuncoes(int id) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "DELETE FROM usuario_funcao WHERE idUsuario="+ id;
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
		con.close();
	}
	
	public List<String> funcoesAtribuidas(int id) throws SQLException {
		Connection con = ds.getConnection();
		String sql = "SELECT funcao FROM usuario_funcao WHERE idUsuario="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<String> usrList = new ArrayList<>();
		while (rs.next()) {
			usrList.add(rs.getString(1));
		}
		con.close();
		return usrList;
	}
};
