package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.quixada.sme.model.PClei;

@ComponentScan(value={"com.quixada.sme"})
@Component
public class PCleiDAO {
	
	@Autowired
	DataSource ds;

	private Connection con;

	public void adiciona(PClei professor) throws SQLException {
		try {
			con = ds.getConnection();

			String INSERT_QUERY = "INSERT INTO pclei "
					+ " (idRegional, idUsuario, nome) "
					+ "values (?, ?, ?)";

			PreparedStatement statement = con.prepareStatement(INSERT_QUERY);
			statement.setInt(1, professor.getIdRegional());
			statement.setInt(2, professor.getIdUsuario());
			statement.setString(3, professor.getNome());
			statement.execute();
		}
		finally {
			if (con != null) con.close();
		}
	}

	public PClei busca(int id) throws SQLException {
		try {
			con = ds.getConnection();

			String SELECT_QUERY = "SELECT * FROM pclei WHERE idProfessor=" + id;

			PreparedStatement statement = con.prepareStatement(SELECT_QUERY);

			ResultSet rs = statement.executeQuery();

			PClei professor = null;
			if (rs.next()) {
				professor = new PClei();
				professor.setIdProfessor(rs.getInt(1));
				professor.setIdRegional(rs.getInt(2));
				professor.setIdUsuario(rs.getInt(3));
				professor.setNome(rs.getString(4));
			}
			return professor;
		}
		finally {
			if (con != null) con.close();
		}
		
	}	
	/*
	 * Retorna um pc lei que tenha o id de usuario informado
	 */
	public PClei buscaPorIdUsuario(int idUsuario) throws SQLException {
		try {
            con = ds.getConnection();

            String SELECT_QUERY = "SELECT * FROM pclei WHERE idUsuario=" + idUsuario;

            PreparedStatement statement = con.prepareStatement(SELECT_QUERY);

            ResultSet rs = statement.executeQuery();

            PClei professor = null;
            if (rs.next()) {
                professor = new PClei();
                professor.setIdProfessor(rs.getInt(1));
                professor.setIdRegional(rs.getInt(2));
                professor.setIdUsuario(rs.getInt(3));
                professor.setNome(rs.getString(4));
            }
            return professor;
        }
        finally {
            if (con != null) con.close();
        }
    }

	public void editar (PClei professor) throws SQLException {
        try {
            con = ds.getConnection();
            String UPDATE_QUERY = "UPDATE pclei"
                    + "SET idRegional=?, idUsuario=?, nome=?"
                    + "WHERE idProfessor=" + professor.getIdProfessor();

            PreparedStatement statement = con.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, professor.getIdRegional());
            statement.setInt(2, professor.getIdUsuario());
            statement.setString(3, professor.getNome());
            statement.execute();
        }
        finally {
            if (con != null) con.close();
        }
    }

	public void excluir (int id) throws SQLException {
        try {
            con = ds.getConnection();

            String DELETE_QUERY = "DELETE FROM pclei WHERE idProfessor=" + id;
            PreparedStatement statement = con.prepareStatement(DELETE_QUERY);
            statement.execute();
        }
        finally {
            if (con != null) con.close();
        }
    }
}