package com.quixada.sme.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quixada.sme.factory.ConnectionFactory;
import com.quixada.sme.model.Usuario;

@Component
public class UsuarioDAO {
	
	@Autowired
	private Usuario_FuncaoDAO funcaoDAO;
	
	public void adiciona(Usuario usuario) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "INSERT INTO usuario "
				+ "( email, senha, isProfCoordenadorLei, isAdmin, isProfessor) "
				+ "VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, usuario.getEmail());
		stmt.setString(2, usuario.getSenha());
		ResultSet rs = stmt.executeQuery();
		//Adiciona funcao do usuario
		if (usuario.getIsAdmin()==1) {
			funcaoDAO.adicionaFuncao(rs.getInt("idUsuario"), "ADMIN");
		}
		if (usuario.getIsProfCoordenadorLei()==1) {
			funcaoDAO.adicionaFuncao(rs.getInt("idUsuario"), "PCLEI");
		}
		if (usuario.getIsProfessor()==1) {
			funcaoDAO.adicionaFuncao(rs.getInt("idUsuario"), "PROFESSOR");
		}
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
			funcaoDAO.fillFuncao(usr);
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
			funcaoDAO.fillFuncao(usr);
		}
		return usr;
	}
	
	/**
	 * Altera somente EMAIL E/OU SENHA do usuario
	 * 
	 * @param usuario Usuario que sera alterado, deve estar com id preenchido
	 * @throws SQLException
	 */
	public void editar(Usuario usuario) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "UPDATE usuario "
				+ "SET email=?, senha=? "
				+ "WHERE idUsuario=" + usuario.getIdUsuario();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, usuario.getEmail());
		stmt.setString(2, usuario.getSenha());
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
			funcaoDAO.fillFuncao(usr);
			usrList.add(usr);
		}
		return usrList;
	}
}


