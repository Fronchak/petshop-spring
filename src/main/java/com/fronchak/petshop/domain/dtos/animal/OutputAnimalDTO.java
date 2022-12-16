package com.fronchak.petshop.domain.dtos.animal;

public class OutputAnimalDTO extends OutputAllAnimalDTO {

	private static final long serialVersionUID = 1L;

	private String description;

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
