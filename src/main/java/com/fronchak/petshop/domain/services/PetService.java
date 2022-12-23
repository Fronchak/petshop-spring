package com.fronchak.petshop.domain.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fronchak.petshop.domain.dtos.pet.InputPetDTO;
import com.fronchak.petshop.domain.dtos.pet.InsertPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputAllPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.dtos.pet.UpdatePetDTO;
import com.fronchak.petshop.domain.entities.Pet;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.DatabaseReferenceException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.PetMapper;
import com.fronchak.petshop.domain.repositories.AnimalRepository;
import com.fronchak.petshop.domain.repositories.ClientRepository;
import com.fronchak.petshop.domain.repositories.ColorRepository;
import com.fronchak.petshop.domain.repositories.PetRepository;

@Service
public class PetService {

	@Autowired
	private PetRepository repository;
	
	@Autowired
	private AnimalRepository animalRepository;
	
	@Autowired
	private ColorRepository colorRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private PetMapper mapper;
	
	@Transactional(readOnly = true)
	public OutputPetDTO findById(Long id) {
		Pet entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pet", id));
		return mapper.convertEntityToOutputDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Page<OutputAllPetDTO> findAllPaged(Pageable pageable) {
		Page<Pet> page = repository.findAll(pageable);
		return mapper.convertEntityPageToOutputAllDTOPage(page);
	}
	
	@Transactional
	public OutputPetDTO save(InsertPetDTO dto) {
		try {
			Pet entity = new Pet();
			copyDTOToEntity(dto, entity);
			entity = repository.save(entity);
			return mapper.convertEntityToOutputDTO(entity);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseReferenceException("Error trying to save pet, invalid animal or colors");
		}

	}
	
	protected void copyDTOToEntity(InputPetDTO dto, Pet entity) {
		mapper.copyDTOToEntity(dto, entity);
		entity.setAnimal(animalRepository.getReferenceById(dto.getIdAnimal()));
		entity.getColors().clear();
		dto.getIdColors().forEach(id -> entity.addColor(colorRepository.getReferenceById(id)));;
		entity.setOwner(clientRepository.getReferenceById(dto.getIdClient()));
	}
	
	@Transactional
	public OutputPetDTO update(UpdatePetDTO dto, Long id) {
		try {
			Pet entity = repository.getReferenceById(id);
			copyDTOToEntity(dto, entity);
			entity = repository.save(entity);
			return mapper.convertEntityToOutputDTO(entity);	
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Pet", id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseReferenceException("Error trying to update pet, invalid animal or colors");
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);			
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Pet", id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity error");
		}
	}
}
