package com.quixada.sme.model;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

//Classe utilizada no upload de arquivos - imagens no caso
@JsonIgnoreProperties({"bytes"})
public class MetaImagem {
	private int id;
	private int idPost;
	private String fileName;
	private String fileSize;
	private String fileType;
	
	private byte[] bytes;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdPost() {
		return idPost;
	}
	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}
	public String generateBase64Image()
	{
	    return Base64.encodeBase64String(this.getBytes());
	}
}
