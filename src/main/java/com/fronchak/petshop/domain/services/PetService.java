package com.fronchak.petshop.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.entities.Pet;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.PetMapper;
import com.fronchak.petshop.domain.repositories.PetRepository;

@Service
public class PetService {

	@Autowired
	private PetRepository repository;
	
	@Autowired
	private PetMapper mapper;
	
	@Transactional
	public OutputPetDTO findById(Long id) {
		Pet entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pet", id));
		return mapper.convertEntityToOutputDTO(entity);
	}
}
