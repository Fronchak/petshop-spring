package com.fronchak.petshop.domain.dtos.animal;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class InputAnimalDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@NotBlank(message = "Description cannot be empty")
	private String description;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
