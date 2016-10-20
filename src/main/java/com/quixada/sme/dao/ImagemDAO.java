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

import com.quixada.sme.model.MetaImagem;

@ComponentScan(value={"com.quixada.sme"})
@Component
public class ImagemDAO {

	@Autowired
	DataSource ds;

	private Connection con;
	
	public void adiciona(MetaImagem imagem) throws SQLException {
		try {
			con = ds.getConnection();

			String INSERT_QUERY = "INSERT INTO imagem "
					+ " (idPost,nome,tamanho,tipo,bytes) "
					+ "values (?,?,?,?,?)";

			PreparedStatement statement = con.prepareStatement(INSERT_QUERY);
			statement.setInt(1, imagem.getIdPost());
			statement.setString(2, imagem.getFileName());
			statement.setString(3, imagem.getFileSize());
			statement.setString(4, imagem.getFileType());
			Blob imgBytes = new SerialBlob(imagem.getBytes());
			statement.setBlob(5, imgBytes);
			statement.execute();
		}
		finally {
			if (con != null) con.close();
		}
	}

	public MetaImagem busca(int id) throws SQLException {
        try {
            con = ds.getConnection();

            String SELECT_QUERY = "SELECT * FROM imagem WHERE idImagem=" + id;

            PreparedStatement statement = con.prepareStatement(SELECT_QUERY);

            ResultSet rs = statement.executeQuery();

            MetaImagem imagem = null;
            if (rs.next()) {
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
        finally {
            if (con != null) con.close();
        }
    }
	
	public List<MetaImagem> buscaPorIdPost(int idPost) throws SQLException {
		try {
            con = ds.getConnection();

            String SELECT_QUERY = "SELECT * FROM imagem WHERE idPost=" + idPost;

            PreparedStatement statement = con.prepareStatement(SELECT_QUERY);

            ResultSet rs = statement.executeQuery();
            List<MetaImagem> result = new ArrayList<MetaImagem>();
            MetaImagem imagem = null;
            while (rs.next()) {
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
        finally {
            if (con != null) con.close();
        }
    }
	
	

	public void excluir (int id) throws SQLException {
        try {
            con = ds.getConnection();

            String DELETE_QUERY = "DELETE FROM imagem WHERE idImagem=" + id;
            PreparedStatement statement = con.prepareStatement(DELETE_QUERY);
            statement.execute();
        }
        finally {
            if (con != null) con.close();
        }
    }
	public void excluirPorIdPost(int idPost) throws SQLException {
        try {
            con = ds.getConnection();

            String DELETE_QUERY = "DELETE FROM imagem WHERE idPost=" + idPost;
            PreparedStatement statement = con.prepareStatement(DELETE_QUERY);
            statement.execute();
        }
        finally {
            if (con != null) con.close();
        }
    }
}
