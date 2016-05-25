package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.quixada.sme.factory.ConnectionFactory;
import com.quixada.sme.model.Imagem;



public class ImagemDAO {

	public void adiciona(Imagem imagem) throws SQLException {
		Connection conexao = ConnectionFactory.getMySqlConnection();

		String INSERT_QUERY = "INSERT INTO imagem "
				+ " (idPost, linkImagem) "
				+ "values (?, ?)";

		PreparedStatement statement = conexao.prepareStatement(INSERT_QUERY);
		statement.setInt(1, imagem.getIdImagem());
		statement.setInt(2, imagem.getIdPost());
		statement.execute();
	}

	public Imagem busca(int id) throws SQLException {
		Connection conexao = ConnectionFactory.getMySqlConnection();

		String SELECT_QUERY = "SELECT * FROM imagem WHERE idImagem=" + id;

		PreparedStatement statement = conexao.prepareStatement(SELECT_QUERY);

		ResultSet rs = statement.executeQuery();

		Imagem imagem = null;
		if(rs.next()) {
			imagem = new Imagem();
			imagem.setIdImagem(rs.getInt(1));
			imagem.setIdPost(rs.getInt(2));
			imagem.setLinkImagem(rs.getString(3));
		}
		return imagem;
	}	

	public void editar (Imagem imagem) throws SQLException {
		Connection conexao = ConnectionFactory.getMySqlConnection();
		String UPDATE_QUERY = "UPDATE imagem"
				+ "SET idPost=?, linkImagem=?" 
				+ "WHERE idImagem=" +  imagem.getIdImagem();

		PreparedStatement statement = conexao.prepareStatement(UPDATE_QUERY);
		statement.setInt(1, imagem.getIdPost());
		statement.setString(2, imagem.getLinkImagem());
		statement.execute();
	}

	public void excluir (int id) throws SQLException {
		Connection conexao = ConnectionFactory.getMySqlConnection();

		String DELETE_QUERY = "DELETE FROM imagem WHERE idImagem=" + id;
		PreparedStatement statement = conexao.prepareStatement(DELETE_QUERY);
		statement.execute();
	}
}
