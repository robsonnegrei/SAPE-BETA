package com.quixada.sme.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

//Classe utilizada no upload de arquivos - imagens no caso
@JsonIgnoreProperties({"bytes"})
public class MetaImagem {
	private int id;
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
	
}
