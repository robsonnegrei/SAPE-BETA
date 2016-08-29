package com.quixada.sme.model;

public class Aluno {
	private int idAluno;
	private int idEscola;
	private String nome;
	private String nivel_atual;
	
	public Aluno(){}
	public Aluno(int idAluno, int idEscola, String nome, String nivel) {
		this.idAluno = idAluno;
		this.idEscola = idEscola;
		this.nome = nome;
		this.nivel_atual = nivel;
	}
	public int getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}
	public int getIdEscola() {
		return idEscola;
	}
	public void setIdEscola(int idEscola) {
		this.idEscola = idEscola;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNivel_atual() {
		return nivel_atual;
	}
	public void setNivel_atual(String nivel_atual) {
		this.nivel_atual = nivel_atual;
	}
	
	
}
