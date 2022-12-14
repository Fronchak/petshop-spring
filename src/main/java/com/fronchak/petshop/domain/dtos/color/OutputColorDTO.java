package com.fronchak.petshop.domain.dtos.color;

import java.io.Serializable;

public class OutputColorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String rgb;
	private String hex;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRgb() {
		return rgb;
	}
	
	public void setRgb(String rgb) {
		this.rgb = rgb;
	}
	
	public String getHex() {
		return hex;
	}
	
	public void setHex(String hex) {
		this.hex = hex;
	}
}
