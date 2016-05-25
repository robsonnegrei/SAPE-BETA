package com.quixada.sme.model;

import java.sql.Date;

public class Avaliacao {
	private int idAvaliacao;
	private Date ano;
	private Date data;
	private int periodo;
	private int idAluno;
	
	public Avaliacao(){
		
	}
	public Avaliacao(int idAvaliacao, Date ano, Date data, int periodo, int idAluno) {
		this.idAvaliacao = idAvaliacao;
		this.ano = ano;
		this.data = data;
		this.periodo = periodo;
		this.idAluno = idAluno;
	}
	public int getIdAvaliacao() {
		return idAvaliacao;
	}
	public void setIdAvaliacao(int idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}
	public Date getAno() {
		return ano;
	}
	public void setAno(Date ano) {
		this.ano = ano;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public int getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}

	
}
