package com.quixada.sme.model;

import org.springframework.stereotype.Component;

@Component
public class Regional {
	private int idRegional;
	private String nome;
	
	public Regional(){}
	public Regional(int idRegional, String nome) {
		this.idRegional = idRegional;
		this.nome = nome;
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
