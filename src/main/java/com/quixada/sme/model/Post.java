package com.quixada.sme.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

public class Post {
	private int idPost;
	private int idProfessor;
	@NotNull
	private String mensagem;
	private Date data;
	
	public Post(){}
	public Post(int idPost, int idProfessor, String mensagem, Date data) {
		this.idPost = idPost;
		this.idProfessor = idProfessor;
		this.mensagem = mensagem;
		this.data = data;
	}
	public int getIdPost() {
		return idPost;
	}
	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}
	public int getIdProfessor() {
		return idProfessor;
	}
	public void setIdProfessor(int idProfessor) {
		this.idProfessor = idProfessor;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	
}
