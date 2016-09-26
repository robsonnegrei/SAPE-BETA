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

import com.mysql.jdbc.Statement;
import com.quixada.sme.model.Usuario;

@Component
public class UsuarioDAO {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	private Usuario_FuncaoDAO funcaoDAO;
	
	public void adiciona(Usuario usuario) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "INSERT INTO usuario "
				+ "( email, senha) "
				+ "VALUES (?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, usuario.getEmail());
		stmt.setString(2, usuario.getSenha());
		stmt.execute();
		ResultSet result = stmt.getGeneratedKeys();
		if(result.next()){
			usuario.setIdUsuario(result.getInt(1));
		}
		//Adiciona funcao do usuario
		if (usuario.getIsAdmin()==1) {
			funcaoDAO.adicionaFuncao(usuario.getIdUsuario(), "ADMIN");
		}
		if (usuario.getIsProfCoordenadorLei()==1) {
			funcaoDAO.adicionaFuncao(usuario.getIdUsuario(), "PCLEI");
		}
		if (usuario.getIsProfessor()==1) {
			funcaoDAO.adicionaFuncao(usuario.getIdUsuario(), "PROFESSOR");
		}
		con.close();
	}
	
	public Usuario buscar(String email) throws SQLException{
		Connection con = ds.getConnection();
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
		con.close();
		return usr;
	}
	
	public Usuario buscar(int id) throws SQLException{
		Connection con = ds.getConnection();
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
		con.close();
		return usr;
	}
	
	/**
	 * Altera somente EMAIL E/OU SENHA do usuario
	 * 
	 * @param usuario Usuario que sera alterado, deve estar com id preenchido
	 * @throws SQLException
	 */
	public void editar(Usuario usuario) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "UPDATE usuario "
				+ "SET email=?, senha=? "
				+ "WHERE idUsuario=" + usuario.getIdUsuario();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, usuario.getEmail());
		stmt.setString(2, usuario.getSenha());
		stmt.execute();
		con.close();
	}
	
	public void excluir(int id) throws SQLException{
		funcaoDAO.limparFuncoes(id);
		Connection con = ds.getConnection();
		String sql = "DELETE FROM usuario WHERE idUsuario="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
		con.close();
	}
	
	public List<Usuario> listarTodos() throws SQLException {
		Connection con = null;
		try{
			con = ds.getConnection();
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
		finally{
			if (con != null) {
				con.close();
			}		
		}
		
	}
}


