package com.quixada.sme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.quixada.sme.model.Avaliacao;
import com.quixada.sme.model.Nivel;

@ComponentScan(value={"com.quixada.sme"})
@Component
public class AvaliacaoDAO {

	@Autowired
	DataSource ds;

	public void adiciona(Avaliacao aval) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "INSERT INTO avaliacao "
				+ "( ano, data, periodo, idAluno, nivel) "
				+ "VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, aval.getAno());
		stmt.setTimestamp(2, aval.getData());
		stmt.setInt(3, aval.getPeriodo());
		stmt.setInt(4, aval.getIdAluno());
		stmt.setString(5, aval.getNivel());
		stmt.execute();
	}
	
	public Avaliacao buscar(int id) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "SELECT * FROM avaliacao WHERE idAvaliacao="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Avaliacao aval = null;
		if (rs.next()) {
			aval = new Avaliacao();
			aval.setIdAvaliacao(rs.getInt(1));
			aval.setAno(rs.getInt(2));
			aval.setData(rs.getTimestamp(3));
			aval.setPeriodo(rs.getInt(4));
			aval.setIdAluno(rs.getInt(5));
		}
		return aval;
	}
//Atualizar
//
//	public void editar(Avaliacao aval) throws SQLException{
//		Connection con = ds.getConnection();
//		String sql = "UPDATE avaliacao "
//				+ "SET ano=?, data=?, periodo=?, idAluno=? "
//				+ "WHERE idAvaliacao=" + aval.getIdAvaliacao();
//		
//		PreparedStatement stmt = con.prepareStatement(sql);
//		stmt.setDate(1, aval.getAno());
//		stmt.setDate(2, aval.getData());
//		stmt.setInt(3, aval.getPeriodo());
//		stmt.setInt(4, aval.getIdAluno());
//		stmt.setString(5, aval.getNomeAluno());
//		stmt.execute();
//	}
	
	public void excluir(int id) throws SQLException{
		Connection con = ds.getConnection();
		String sql = "DELETE FROM avaliacao WHERE idAvaliacao="+ id;
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
	}
	
	public int resultAvaliacaoPorPeriodo(int idEscola, int periodo, String nivel) throws SQLException{
		Connection con = ds.getConnection();
		//String sql = "select COUNT(*) from aluno join avaliacao"
		//		+ " ON aluno.idAluno = avaliacao.idAluno where aluno.idEscola = ? and avaliacao.nivel = ? and avaliacao.periodo = ?";
		String sql =  "select COUNT(*) from aluno join avaliacao ON aluno.idAluno = avaliacao.idAluno where aluno.idEscola = ? and avaliacao.nivel = ? and avaliacao.periodo = ?;";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, idEscola);
		stmt.setString(2, nivel);
		stmt.setInt(3, periodo);
	
		ResultSet rs = stmt.executeQuery();
		int resultado = 0;
		if(rs.next())
			 resultado = rs.getInt(1);
		
		return resultado;
	}
}
