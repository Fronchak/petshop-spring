package com.fronchak.petshop.domain.mappers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fronchak.petshop.domain.dtos.animal.InputAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAllAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.entities.Animal;

@Service
public class AnimalMapper {

	public OutputAnimalDTO convertEntityOutputDTO(Animal entity) {
		OutputAnimalDTO dto = new OutputAnimalDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		return dto;
	}
	
	public void copyInputDTOToEntity(InputAnimalDTO dto, Animal entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
	}
	
	public Page<OutputAllAnimalDTO> convertEntityPageToOutputAllDTOPage(Page<Animal> page) {
		return page.map(entity -> convertEntityToOutputAllAnimalDTO(entity));
	}
	
	private OutputAllAnimalDTO convertEntityToOutputAllAnimalDTO(Animal entity) {
		OutputAllAnimalDTO dto = new OutputAllAnimalDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
}
