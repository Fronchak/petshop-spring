package com.fronchak.petshop.domain.dtos.pet;

import java.util.List;

public class OutputPetDTO {

	private Long id;
	private String name;
	private Double weightInKg;
	private Double heightInCm;
	private PetAnimalOutputDTO animal;
	private List<PetColorOutputDTO> colors;
	
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

	public PetAnimalOutputDTO getAnimal() {
		return animal;
	}

	public void setAnimal(PetAnimalOutputDTO animal) {
		this.animal = animal;
	}

	public List<PetColorOutputDTO> getColors() {
		return colors;
	}
	
	public void setColors(List<PetColorOutputDTO> colors) {
		this.colors = colors;
	}

	public void addColor(PetColorOutputDTO color) {
		colors.add(color);
	}
	
	public static class PetAnimalOutputDTO {
		
		private Long id;
		private String name;
		
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
	}
	
	public static class PetColorOutputDTO {
		
		private Long id;
		private String name;
		
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
	}
}
