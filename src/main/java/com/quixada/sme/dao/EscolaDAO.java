package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.quixada.sme.model.Escola;
import com.quixada.sme.sape.config.AppConfig;


@Component
@ComponentScan(value={"com.quixada.sme.sape.config"})
public class EscolaDAO {
	
	@Autowired
	DataSource ds;
	
	public ArrayList<Escola> getEscolas() throws SQLException{
		Connection con = ds.getConnection();
		String sql = "SELECT * FROM escola";
		PreparedStatement stm = con.prepareStatement(sql);
		ArrayList<Escola> escolas = new ArrayList<>();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()){
			Escola e = new Escola(rs.getInt("idEscola"),rs.getInt("idRegional") ,rs.getString("nome"));
			escolas.add(e);
		}
		return escolas;
	}
	
	public Escola getEscolasPorID(int idEscola) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "SELECT * FROM escola where idEscola ="+idEscola;
		PreparedStatement stm = con.prepareStatement(sql);
		ResultSet rs = stm.executeQuery(sql);
		Escola escola = null;
		if (rs.next()) {
			escola = new Escola();
			escola.setIdEscola(rs.getInt(1));
			escola.setIdRegional(rs.getInt(2));
			escola.setNome(rs.getString(3));
		}
		return escola;
	}
	
	public void addEscola( Escola escola) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "INSERT INTO escola"
				+ "(idEscola, idRegional, nome) values (?, ?, ?) " ;
		PreparedStatement stm = con.prepareStatement(sql);
		stm.setInt(1, escola.getIdEscola());
		stm.setInt(2, escola.getIdRegional());
		stm.setString(3, escola.getNome());
		stm.execute();
	}
	public void editar(Escola escola) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "UPDATE escola "
				+ "SET idEscola=?, idRegional=?, nome=? "
				+ "WHERE idEscola=" + escola.getIdEscola();
		
		PreparedStatement stmt =  con.prepareStatement(sql);
		stmt.setInt(1, escola.getIdEscola());
		stmt.setInt(2, escola.getIdRegional());
		stmt.setString(3, escola.getNome());
		stmt.execute();
	}
	public void excluir(int idEscola) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "DELETE FROM escola WHERE idEscola="+ idEscola;
		PreparedStatement stmt =  con.prepareStatement(sql);
		stmt.execute();
	}
	public ArrayList<Escola> getEscolasRegional(int idRegional) throws SQLException{
		String sql = "SELECT * FROM escola WHERE idRegional="+idRegional;
		Connection con = ds.getConnection();
		PreparedStatement stm = con.prepareStatement(sql);
		//stm.setInt(1, idRegional);
		ArrayList<Escola> escolas = new ArrayList<>();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()){
			Escola e = new Escola(rs.getInt("idEscola"),rs.getInt("idRegional") ,rs.getString("nome"));
			escolas.add(e);
		}
		return escolas;
	}

}
