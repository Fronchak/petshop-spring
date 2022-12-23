package com.fronchak.petshop.domain.dtos.pet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class InputPetDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Pet's name cannot be empty")
	private String name;
	
	@Positive(message = "Pet's weight cannot be negative")
	@DecimalMax(value = "50.0", message = "Pet's weight cannot be bigger than 50.0 kg")
	@NotNull(message = "Pet's weight must be specified")
	private Double weightInKg;
	
	@NotNull(message = "Pet's height must be specified")
	@Positive(message = "Pet's height must be a positive value")
	@DecimalMax(value = "200.0", message = "Pet's height cannot be bigger than 200 cm")
	private Double heightInCm;
	
	@NotNull(message = "Pet's type of animal must be specified")
	private Long idAnimal;
	
	@NotEmpty(message = "Pet must have at least one color")
	private List<@NotNull(message = "Pet's color cannot be null") Long> idColors = new ArrayList<>();
	
	@NotNull(message = "Pet's owner must be specified")
	private Long idClient;
	
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

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}
}
