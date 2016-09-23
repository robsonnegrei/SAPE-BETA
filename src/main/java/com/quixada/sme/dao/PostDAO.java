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
import com.quixada.sme.model.Post;

@Component
public class PostDAO {
	
	@Autowired
	DataSource ds;
	
	public Post adiciona(Post post) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "INSERT INTO post "
				+ "( idProfessor, mensagem, data) "
				+ "VALUES ( ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, post.getIdProfessor());
		stmt.setString(2, post.getMensagem());
		stmt.setTimestamp(3, post.getData());
		stmt.execute();
		ResultSet result = stmt.getGeneratedKeys();
		if(result.next()){
			post.setIdPost(result.getInt(1));
			return post;
		}
		con.close();
		return null;
	}
	
	
	public Post buscar(int id) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "SELECT * FROM post WHERE idPost="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Post post = null;
		if (rs.next()) {
			post = new Post();
			post.setIdPost(rs.getInt(1));
			post.setIdProfessor(rs.getInt(2));
			post.setMensagem(rs.getString(3));
			post.setData(rs.getTimestamp(4));
		}
		con.close();
		return post;
	}

	public void editar(Post post) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "UPDATE post "
				+ "SET idProfessor=?, mensagem=?, data=? "
				+ "WHERE idPost=" + post.getIdPost();
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, post.getIdProfessor());
		stmt.setString(2, post.getMensagem());
		stmt.setTimestamp(3, post.getData());
		stmt.execute();
	}
	
	public void excluir(int id) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "DELETE FROM post WHERE idPost="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
		con.close();
	}
	
	public List<Post> listarTodos() throws SQLException {
		Connection con = ds.getConnection();
		String sql = "SELECT * FROM post";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Post> postList = new ArrayList<>();
		while (rs.next()) {
			Post postagem = new Post();
			postagem.setIdPost(rs.getInt(1));
			postagem.setIdProfessor(rs.getInt(2));
			postagem.setMensagem(rs.getString(3));
			postagem.setData(rs.getTimestamp(4));
			postList.add(postagem);
		}
		con.close();
		return postList;
	}
}
