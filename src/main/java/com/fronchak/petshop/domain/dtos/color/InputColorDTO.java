package com.fronchak.petshop.domain.dtos.color;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class InputColorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@NotBlank(message = "Rgb cannot be empty")
	private String rgb;
	
	@NotBlank(message = "Hex cannot be empty")
	private String hex;
	
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
