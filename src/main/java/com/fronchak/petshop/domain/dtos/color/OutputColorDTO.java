package com.fronchak.petshop.domain.dtos.color;

import java.io.Serializable;

public class OutputColorDTO extends OutputAllColorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String rgb;
	private String hex;

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
