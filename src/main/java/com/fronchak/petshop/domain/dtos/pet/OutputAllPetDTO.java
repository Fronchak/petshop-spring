package com.fronchak.petshop.domain.dtos.pet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OutputAllPetDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private PetAnimalOutputDTO animal;
	private List<PetColorOutputDTO> colors = new ArrayList<>();
	
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
