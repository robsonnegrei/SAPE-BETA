package com.quixada.sme.model;

public class Imagem {
	private int idImagem;
	private int idPost;
	private String linkImagem;
	
	
	public Imagem(int idImagem, int idPost, String linkImagem) {
		this.idImagem = idImagem;
		this.idPost = idPost;
		this.linkImagem = linkImagem;
	}
	public int getIdImagem() {
		return idImagem;
	}
	public void setIdImagem(int idImagem) {
		this.idImagem = idImagem;
	}
	public int getIdPost() {
		return idPost;
	}
	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}
	public String getLinkImagem() {
		return linkImagem;
	}
	public void setLinkImagem(String linkImagem) {
		this.linkImagem = linkImagem;
	}
	
	

}
