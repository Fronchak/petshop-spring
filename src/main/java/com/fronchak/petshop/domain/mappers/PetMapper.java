package com.fronchak.petshop.domain.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fronchak.petshop.domain.dtos.pet.InputPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputAllPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.domain.entities.Client;
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
		dto.setOwner(convertEntityToPetClientOutputDTO(entity.getOwner()));
		return dto;
	}
	
	private OutputAllPetDTO.PetAnimalOutputDTO convertEntityToPetAnimalOutputDTO(Animal entity) {
		OutputAllPetDTO.PetAnimalOutputDTO dto = new OutputAllPetDTO.PetAnimalOutputDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
	
	private List<OutputAllPetDTO.PetColorOutputDTO> convertEntityListToPetColorOutputDTOList(List<Color> list) {
		return list.stream()
			.map(entity -> convertEntityToPetColorOutputDTO(entity))
			.collect(Collectors.toList());	
	}
	
	private OutputAllPetDTO.PetColorOutputDTO convertEntityToPetColorOutputDTO(Color entity) {
		OutputAllPetDTO.PetColorOutputDTO dto = new OutputAllPetDTO.PetColorOutputDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
	
	private OutputAllPetDTO.PetClientOutputDTO convertEntityToPetClientOutputDTO(Client entity) {
		OutputAllPetDTO.PetClientOutputDTO dto = new OutputAllPetDTO.PetClientOutputDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getFirstName() + " " + entity.getLastName());
		return dto;
	}
	
	public void copyDTOToEntity(InputPetDTO dto, Pet entity) {
		entity.setName(dto.getName());
		entity.setWeightInKg(dto.getWeightInKg());
		entity.setHeightInCm(dto.getHeightInCm());
	}
	
	public Page<OutputAllPetDTO> convertEntityPageToOutputAllDTOPage(Page<Pet> page) {
		return page.map(entity -> convertEntityToOutputAllDTO(entity));		
	}
	
	private OutputAllPetDTO convertEntityToOutputAllDTO(Pet entity) {
		OutputAllPetDTO mock = new OutputAllPetDTO();
		mock.setId(entity.getId());
		mock.setName(entity.getName());
		mock.setAnimal(convertEntityToPetAnimalOutputDTO(entity.getAnimal()));
		mock.setColors(convertEntityListToPetColorOutputDTOList(entity.getColors()));
		mock.setOwner(convertEntityToPetClientOutputDTO(entity.getOwner()));
		return mock;
	}
}
