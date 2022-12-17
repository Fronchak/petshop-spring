package com.fronchak.petshop.domain.dtos.pet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InputPetDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private Double weightInKg;
	private Double heightInCm;
	
	private Long idAnimal;
	private List<Long> idColors = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getWeightInKg() {
		return weightInKg;
	}

	public void setWeightInKg(Double weightInKg) {
		this.weightInKg = weightInKg;
	}

	public Double getHeightInCm() {
		return heightInCm;
	}

	public void setHeightInCm(Double heightInCm) {
		this.heightInCm = heightInCm;
	}
	
	public Long getIdAnimal() {
		return idAnimal;
	}
	
	public void setIdAnimal(Long idAnimal) {
		this.idAnimal = idAnimal;
	}
	
	public List<Long> getIdColors() {
		return idColors;
	}
	
	public void addIdColor(Long idColor) {
		idColors.add(idColor);
	}
}
