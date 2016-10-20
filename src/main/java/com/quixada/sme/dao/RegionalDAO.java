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

import com.quixada.sme.model.Regional;

@Component
public class RegionalDAO {
	
	@Autowired
	DataSource ds;

	private Connection con;

	public void adiciona(Regional regional) throws SQLException{
        try {
            con = ds.getConnection();
            String sql = "INSERT INTO regional "
                    + "(nome) "
                    + "VALUES (?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(2, regional.getNome());
            stmt.execute();
        }
        finally {
            if (con != null) con.close();
        }
    }
	
	public Regional buscar(int id) throws SQLException{
        try {
            con = ds.getConnection();
            String sql = "SELECT * FROM regional WHERE idRegional=" + id;

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Regional regional = null;
            if (rs.next()) {
                regional = new Regional();
                regional.setIdRegional(rs.getInt(1));
                regional.setNome(rs.getString(2));
            }
            return regional;
        }
        finally {
            if (con != null) con.close();
        }
    }

	public void editar(Regional regional) throws SQLException{
        try {
            con = ds.getConnection();
            String sql = "UPDATE reginoal "
                    + "SET nome=? "
                    + "WHERE idReginonal=" + regional.getIdRegional();

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, regional.getNome());
            stmt.execute();
        }
        finally {
            if (con != null) con.close();
        }
    }
	
	public void excluir(int id) throws SQLException{
        try {
            Connection con = ds.getConnection();
            String sql = "DELETE FROM regional WHERE idRegional=" + id;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.execute();
        }
        finally {
            if (con != null) con.close();
        }
    }
	
	public List<Regional> listar() throws SQLException{
        try {
            con = ds.getConnection();
            String sql = "SELECT * FROM regional";

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<Regional> resultado = new ArrayList<Regional>();
            while (rs.next()) {
                Regional regional = new Regional();
                regional.setIdRegional(rs.getInt(1));
                regional.setNome(rs.getString(2));
                resultado.add(regional);
            }
            return resultado;
        }
        finally {
            if (con != null) con.close();
        }
    }
}
