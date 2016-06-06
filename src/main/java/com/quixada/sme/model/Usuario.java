package com.quixada.sme.model;

public class Usuario {
	private int idUsuario;
	private String email;
	private String senha;
	private int isProfCoordenadorLei;
	private int isAdmin;
	
	public Usuario(){
		
	}
	public Usuario(int idUsuario, String email, String senha, int isProfCoordenadorLei, int isAdmin) {
		// TODO Auto-generated constructor stub
		this.idUsuario = idUsuario;
		this.email = email;
		this.senha = senha;
		this.isProfCoordenadorLei = isProfCoordenadorLei;
		this.isAdmin = isAdmin;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getIsProfCoordenadorLei() {
		return isProfCoordenadorLei;
	}
	public void setIsProfCoordenadorLei(int isProfCoordenadorLei) {
		this.isProfCoordenadorLei = isProfCoordenadorLei;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

}
