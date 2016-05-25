package com.quixada.sme.model;

public class Professor {
	private int idProfessor;
	private int idRegional;
	private int idUsuario;
	private String nome;

	public Professor (){

	}
	public Professor(int idProfessor, int idRegional, int idUsuario, String nome) {
		this.idProfessor = idProfessor;
		this.idRegional = idRegional;
		this.idUsuario = idUsuario;
		this.nome = nome;
	}
	public int getIdProfessor() {
		return idProfessor;
	}
	public void setIdProfessor(int idProfessor) {
		this.idProfessor = idProfessor;
	}
	public int getIdRegional() {
		return idRegional;
	}
	public void setIdRegional(int idRegional) {
		this.idRegional = idRegional;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
