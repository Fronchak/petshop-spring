package com.fronchak.petshop.domain.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fronchak.petshop.domain.dtos.client.InputClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputAllClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.entities.Client;
import com.fronchak.petshop.domain.entities.Pet;

@Service
public class ClientMapper {

	public OutputClientDTO convertEntityToOutputDTO(Client entity) {
		OutputClientDTO dto = new OutputClientDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getFirstName() + " " + entity.getLastName());
		dto.setEmail(entity.getEmail());
		dto.setPets(convertPetEntitySetToClientPetOutputDTOSet(entity.getPets()));
		return dto;
	}
	
	private Set<OutputClientDTO.OutputClientPetDTO> convertPetEntitySetToClientPetOutputDTOSet(Set<Pet> list) {
		return list.stream()
				.map(entity -> convertPetEntityToClientPetOutputDTO(entity))
				.collect(Collectors.toSet());
	}
	
	private OutputClientDTO.OutputClientPetDTO convertPetEntityToClientPetOutputDTO(Pet entity) {
		OutputClientDTO.OutputClientPetDTO dto = new OutputClientDTO.OutputClientPetDTO();
		dto.setId(entity.getId());
		dto.setPetName(entity.getName());
		dto.setAnimalName(entity.getAnimal().getName());
		return dto;
	}
	
	public Page<OutputAllClientDTO> convertEntityPageToOutputAllClientDTO(Page<Client> page) {
		return page.map(entity -> convertEntityToOutputAllClientDTO(entity));
	}
	
	private OutputAllClientDTO convertEntityToOutputAllClientDTO(Client entity) {
		OutputAllClientDTO dto = new OutputAllClientDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getFirstName() + " " + entity.getLastName());
		dto.setEmail(entity.getEmail());
		return dto;
	}
	
	public void copyInputDTOToEntity(InputClientDTO dto, Client entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
	}
}
