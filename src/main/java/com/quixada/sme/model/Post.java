package com.quixada.sme.model;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class Post {
	private int idPost;
	private int idProfessor;
	@NotNull
	private String mensagem;
	private Timestamp data;
	private List<MetaImagem> images;
	
	public Post(){}
	public Post(int idPost, int idProfessor, String mensagem, Timestamp data, List<MetaImagem> images) {
		this.idPost = idPost;
		this.idProfessor = idProfessor;
		this.mensagem = mensagem;
		this.data = data;
		this.images = images;
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
	public List<MetaImagem> getImages() {
		return images;
	}
	public void setImages(List<MetaImagem> images) {
		this.images = images;
	}
	
	
}
