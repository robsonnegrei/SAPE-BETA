package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.quixada.sme.factory.ConnectionFactory;
import com.quixada.sme.model.Post;

@Component
public class PostDAO {
	public void adiciona(Post post) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "INSERT INTO post "
				+ "( idProfessor, mensagem, data) "
				+ "VALUES ( ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, post.getIdProfessor());
		stmt.setString(2, post.getMensagem());
		stmt.setDate(3, post.getData());
		stmt.execute();
	}
	
	public Post buscar(int id) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "SELECT * FROM post WHERE idPost="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Post post = null;
		if (rs.next()) {
			post = new Post();
			post.setIdPost(rs.getInt(1));
			post.setIdProfessor(rs.getInt(2));
			post.setMensagem(rs.getString(3));
			post.setData(rs.getDate(4));
		}
		return post;
	}

	public void editar(Post post) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "UPDATE post "
				+ "SET idProfessor=?, mensagem=?, data=? "
				+ "WHERE idAvaliacao=" + post.getIdPost();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, post.getIdProfessor());
		stmt.setString(2, post.getMensagem());
		stmt.setDate(3, post.getData());
		stmt.execute();
	}
	
	public void excluir(int id) throws SQLException{
		Connection con = ConnectionFactory.getMySqlConnection();
		String sql = "DELETE FROM post WHERE idPost="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
	}

}
