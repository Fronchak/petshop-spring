package com.fronchak.petshop.domain.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fronchak.petshop.domain.dtos.pet.InputPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.domain.entities.Color;
import com.fronchak.petshop.domain.entities.Pet;

@Service
public class PetMapper {

	public OutputPetDTO convertEntityToOutputDTO(Pet entity) {
		OutputPetDTO dto = new OutputPetDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setWeightInKg(entity.getWeightInKg());
		dto.setHeightInCm(entity.getHeightInCm());
		dto.setAnimal(convertEntityToPetAnimalOutputDTO(entity.getAnimal()));
		dto.setColors(convertEntityListToPetColorOutputDTOList(entity.getColors()));
		return dto;
	}
	
	private OutputPetDTO.PetAnimalOutputDTO convertEntityToPetAnimalOutputDTO(Animal entity) {
		OutputPetDTO.PetAnimalOutputDTO dto = new OutputPetDTO.PetAnimalOutputDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
	
	private List<OutputPetDTO.PetColorOutputDTO> convertEntityListToPetColorOutputDTOList(List<Color> list) {
		return list.stream()
			.map(entity -> convertEntityToPetColorOutputDTO(entity))
			.collect(Collectors.toList());
			
	}
	
	private OutputPetDTO.PetColorOutputDTO convertEntityToPetColorOutputDTO(Color entity) {
		OutputPetDTO.PetColorOutputDTO dto = new OutputPetDTO.PetColorOutputDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
	
	public void copyDTOToEntity(InputPetDTO dto, Pet entity) {
		entity.setName(dto.getName());
		entity.setWeightInKg(dto.getWeightInKg());
		entity.setHeightInCm(dto.getHeightInCm());
	}
}
