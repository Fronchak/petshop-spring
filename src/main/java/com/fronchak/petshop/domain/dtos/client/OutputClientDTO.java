package com.fronchak.petshop.domain.dtos.client;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class OutputClientDTO extends OutputAllClientDTO {

	private static final long serialVersionUID = 1L;
	
	private Set<OutputClientPetDTO> pets = new HashSet<>();

	public Set<OutputClientPetDTO> getPets() {
		return pets;
	}

	public void setPets(Set<OutputClientPetDTO> pets) {
		this.pets = pets;
	}
	
	public void addPet(OutputClientPetDTO pet) {
		pets.add(pet);
	}
	
	public static class OutputClientPetDTO implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private Long id;
		private String petName;
		private String animalName;
		
		public OutputClientPetDTO() {}
		
		public OutputClientPetDTO(Long id, String petName, String animalName) {
			this.id = id;
			this.petName = petName;
			this.animalName = animalName;
		}
		
		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		
		public String getPetName() {
			return petName;
		}
		
		public void setPetName(String petName) {
			this.petName = petName;
		}
		
		public String getAnimalName() {
			return animalName;
		}
		
		public void setAnimalName(String animalName) {
			this.animalName = animalName;
		}

		@Override
		public int hashCode() {
			return Objects.hash(animalName, id, petName);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			OutputClientPetDTO other = (OutputClientPetDTO) obj;
			return Objects.equals(animalName, other.animalName) && Objects.equals(id, other.id)
					&& Objects.equals(petName, other.petName);
		}

		@Override
		public String toString() {
			return "OutputClientPetDTO [id=" + id + ", petName=" + petName + ", animalName=" + animalName + "]";
		}
		
		
	}
}
