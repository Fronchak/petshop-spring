package com.fronchak.petshop.domain.dtos.pet;

public class OutputPetDTO extends OutputAllPetDTO {

	private static final long serialVersionUID = 1L;
	
	private Double weightInKg;
	private Double heightInCm;

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
}
