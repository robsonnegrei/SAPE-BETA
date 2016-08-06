package com.quixada.sme.model;

import java.sql.Date;

public class Avaliacao {
	private int idAvaliacao;
	private Date ano;
	private Date data;
	private int periodo;
	private int idAluno;
	private String nomeAluno;
	private String nivel;
	public Avaliacao(){}
	public Avaliacao(int idAvaliacao, Date ano, Date data, int periodo, int idAluno ,String nomeAluno, String nivel) {
		this.idAvaliacao = idAvaliacao;
		this.ano = ano;
		this.data = data;
		this.periodo = periodo;
		this.idAluno = idAluno;
		this.nomeAluno = nomeAluno;
		this.nivel = nivel;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getNomeAluno() {
		return nomeAluno;
	}
	public void setNomeAluno(String nome) {
		this.nomeAluno = nome;
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
