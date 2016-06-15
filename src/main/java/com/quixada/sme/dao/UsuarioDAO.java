package com.quixada.sme.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quixada.sme.factory.ConnectionFactory;
import com.quixada.sme.model.Usuario;

public class UsuarioDAO {
	
	
	public void adiciona(Usuario usuario) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "INSERT INTO usuario "
				+ "( email, senha, isProfCoordenadorLei, isAdmin, isProfessor) "
				+ "VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, usuario.getEmail());
		stmt.setString(2, usuario.getSenha());
		stmt.setInt(3, usuario.getIsProfCoordenadorLei());
		stmt.setInt(4, usuario.getIsAdmin());
		stmt.setInt(5, usuario.getIsProfessor());
		stmt.execute();
	}
	
	public Usuario buscar(String email) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "SELECT * FROM usuario WHERE email='"+email+"'";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		//stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		Usuario usr = null;
		if (rs.next()) {
			usr = new Usuario();
			usr.setIdUsuario(rs.getInt(1));
			usr.setEmail(rs.getString(2));
			usr.setSenha(rs.getString(3));
			usr.setIsProfCoordenadorLei(rs.getInt(4));
			usr.setIsAdmin(rs.getInt(5));
		}
		return usr;
	}
	
	public Usuario buscar(int id) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "SELECT * FROM usuario WHERE idUsuario="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Usuario usr = null;
		if (rs.next()) {
			usr = new Usuario();
			usr.setIdUsuario(rs.getInt(1));
			usr.setEmail(rs.getString(2));
			usr.setSenha(rs.getString(3));
			usr.setIsProfCoordenadorLei(rs.getInt(4));
			usr.setIsAdmin(rs.getInt(5));
		}
		return usr;
	}

	public void editar(Usuario usuario) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "UPDATE usuario "
				+ "SET email=?, senha=?, isProfCoordenadorLei=?, isAdmin=? "
				+ "WHERE idUsuario=" + usuario.getIdUsuario();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, usuario.getEmail());
		stmt.setString(2, usuario.getSenha());
		stmt.setInt(3, usuario.getIsProfCoordenadorLei());
		stmt.setInt(4, usuario.getIsAdmin());
		stmt.execute();
	}
	
	public void excluir(int id) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "DELETE FROM usuario WHERE idUsuario="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
	}
	
	public List<Usuario> listarTodos() throws SQLException {
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "SELECT * FROM usuario";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Usuario> usrList = new ArrayList<>();
		while (rs.next()) {
			Usuario usr = new Usuario();
			usr.setIdUsuario(rs.getInt(1));
			usr.setEmail(rs.getString(2));
			usr.setSenha(rs.getString(3));
			usr.setIsProfCoordenadorLei(rs.getInt(4));
			usr.setIsAdmin(rs.getInt(5));
			usrList.add(usr);
		}
		return usrList;
	}
}


