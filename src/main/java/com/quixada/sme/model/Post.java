package com.quixada.sme.model;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Post {
	private int idPost;
	private int idProfessor;
	@NotNull
	@NotEmpty
	private String mensagem;
	private Timestamp data;
	private List<MetaImagem> images;
	
	public Post(){}
	public Post(int idPost, int idProfessor, String mensagem, Timestamp data, List<MetaImagem> linkImages) {
		this.idPost = idPost;
		this.idProfessor = idProfessor;
		this.mensagem = mensagem;
		this.data = data;
		this.images = linkImages;
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
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public List<MetaImagem> getLinkImages() {
		return images;
	}
	public void setLinkImages(List<MetaImagem> linkImages) {
		this.images = linkImages;
	}
	
	
}
