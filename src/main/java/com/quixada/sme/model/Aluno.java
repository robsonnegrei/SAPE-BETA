package com.quixada.sme.model;

import org.springframework.stereotype.Component;

@Component
public class Aluno {
	private int idAluno;
	private int idEscola;
	private String nome;
	private String nivel;
	
	public Aluno(){}
	public Aluno(int idAluno, int idEscola, String nome, String nivel) {
		this.idAluno = idAluno;
		this.idEscola = idEscola;
		this.nome = nome;
		this.nivel = nivel;
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
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	
}
