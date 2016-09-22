package com.quixada.sme.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.quixada.sme.model.MetaImagem;;

@ComponentScan(value={"com.quixada.sme"})
@Component
public class ImagemDAO {

	@Autowired
	DataSource ds;
	
	public void adiciona(MetaImagem imagem) throws SQLException {
		Connection conexao = ds.getConnection();

		String INSERT_QUERY = "INSERT INTO imagem "
				+ " (idPost,nome,tamanho,tipo,bytes) "
				+ "values (?,?,?,?,?)";

		PreparedStatement statement = conexao.prepareStatement(INSERT_QUERY);
		statement.setInt(1, imagem.getIdPost());
		statement.setString(2, imagem.getFileName());
		statement.setString(3, imagem.getFileSize());
		statement.setString(4, imagem.getFileType());
		Blob imgBytes = new SerialBlob(imagem.getBytes());
		statement.setBlob(5, imgBytes);
		statement.execute();
	}

	public MetaImagem busca(int id) throws SQLException {
		Connection conexao = ds.getConnection();

		String SELECT_QUERY = "SELECT * FROM imagem WHERE idImagem=" + id;

		PreparedStatement statement = conexao.prepareStatement(SELECT_QUERY);

		ResultSet rs = statement.executeQuery();

		MetaImagem imagem = null;
		if(rs.next()) {
			imagem = new MetaImagem();
			imagem.setId(rs.getInt(1));
			imagem.setIdPost(rs.getInt(2));
			imagem.setFileName(rs.getString(3));
			imagem.setFileSize(rs.getString(4));
			imagem.setFileType(rs.getString(5));
			Blob blob = rs.getBlob(6);
			int blobLength = (int) blob.length();  
			byte[] blobAsBytes = blob.getBytes(1, blobLength);
			imagem.setBytes(blobAsBytes);
			blob.free();
		}
		return imagem;
	}	
	
	public List<MetaImagem> buscaPorIdPost(int idPost) throws SQLException {
		Connection conexao = ds.getConnection();

		String SELECT_QUERY = "SELECT * FROM imagem WHERE idPost=" + idPost;

		PreparedStatement statement = conexao.prepareStatement(SELECT_QUERY);

		ResultSet rs = statement.executeQuery();
		List<MetaImagem> result = new ArrayList<MetaImagem>();
		MetaImagem imagem = null;
		while(rs.next()) {
			imagem = new MetaImagem();
			imagem.setId(rs.getInt(1));
			imagem.setIdPost(rs.getInt(2));
			imagem.setFileName(rs.getString(3));
			imagem.setFileSize(rs.getString(4));
			imagem.setFileType(rs.getString(5));
			Blob blob = rs.getBlob(6);
			int blobLength = (int) blob.length();  
			byte[] blobAsBytes = blob.getBytes(1, blobLength);
			imagem.setBytes(blobAsBytes);
			blob.free();
			
			result.add(imagem);
		}
		return result;
	}	
	
	

	public void excluir (int id) throws SQLException {
		Connection conexao = ds.getConnection();

		String DELETE_QUERY = "DELETE FROM imagem WHERE idImagem=" + id;
		PreparedStatement statement = conexao.prepareStatement(DELETE_QUERY);
		statement.execute();
	}
	public void excluirPorIdPost(int idPost) throws SQLException {
		Connection conexao = ds.getConnection();

		String DELETE_QUERY = "DELETE FROM imagem WHERE idPost=" + idPost;
		PreparedStatement statement = conexao.prepareStatement(DELETE_QUERY);
		statement.execute();
	}
}
