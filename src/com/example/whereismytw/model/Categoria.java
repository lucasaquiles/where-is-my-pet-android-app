package com.example.whereismytw.model;

import java.io.Serializable;

public class Categoria implements Serializable{
	
	private String id;
	private String nome;
	private String tag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@Override
	public String toString() {
	
		return getNome();
	}
}
