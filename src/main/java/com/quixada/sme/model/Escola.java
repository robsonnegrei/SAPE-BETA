package com.quixada.sme.model;

public class Escola {
	private int idEscola;
	private int idRegional;
	private String nome;
	
	
	public Escola(int idEscola, int idRegional, String nome) {
		this.idEscola = idEscola;
		this.idRegional = idRegional;
		this.nome = nome;
	}
	public int getIdEscola() {
		return idEscola;
	}
	public void setIdEscola(int idEscola) {
		this.idEscola = idEscola;
	}
	public int getIdRegional() {
		return idRegional;
	}
	public void setIdRegional(int idRegional) {
		this.idRegional = idRegional;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
