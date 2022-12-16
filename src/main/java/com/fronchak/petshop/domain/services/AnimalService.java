package com.fronchak.petshop.domain.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fronchak.petshop.domain.dtos.animal.InsertAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAllAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.UpdateAnimalDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.AnimalMapper;
import com.fronchak.petshop.domain.repositories.AnimalRepository;

@Service
public class AnimalService {

	@Autowired
	private AnimalRepository repository;
	
	@Autowired
	private AnimalMapper mapper;
	
	@Transactional(readOnly = true)
	public OutputAnimalDTO findById(Long id) {
		Animal entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Animal not found by ID: " + id));
		return mapper.convertEntityOutputDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Page<OutputAllAnimalDTO> findAllPaged(Pageable pageable) {
		Page<Animal> entityPage = repository.findAll(pageable);
		return mapper.convertEntityPageToOutputAllDTOPage(entityPage);
	}
	
	@Transactional
	public OutputAnimalDTO save(InsertAnimalDTO insertDTO) {
		Animal entity = new Animal();
		mapper.copyInputDTOToEntity(insertDTO, entity);
		entity = repository.save(entity);
		return mapper.convertEntityOutputDTO(entity);
	}
	
	@Transactional
	public OutputAnimalDTO update(UpdateAnimalDTO updateDTO, Long id) {
		try {
			Animal entity = repository.getReferenceById(id);
			mapper.copyInputDTOToEntity(updateDTO, entity);
			entity = repository.save(entity);
			return mapper.convertEntityOutputDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Animal not found by ID: " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);	
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Animal not found by ID: " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity exception");
		}
	}
}
