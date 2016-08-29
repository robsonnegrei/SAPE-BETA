package com.quixada.sme.model;

import java.sql.Timestamp;

public class Avaliacao {
	private int idAvaliacao;
	private int ano;
	private Timestamp data;
	private int periodo; //1,2,3,4,5
	private int idAluno;
	private String nomeAluno;
	private String nivel;
	
	public Avaliacao(){}
	public Avaliacao(int idAvaliacao, int ano, Timestamp data, int periodo, int idAluno ,String nomeAluno, String nivel) {
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
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
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
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	
}
