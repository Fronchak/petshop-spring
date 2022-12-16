package com.fronchak.petshop.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fronchak.petshop.domain.dtos.animal.InsertAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAllAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.UpdateAnimalDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.AnimalMapper;
import com.fronchak.petshop.domain.repositories.AnimalRepository;

@Service
public class AnimalService {

	@Autowired
	private AnimalRepository repository;
	
	@Autowired
	private AnimalMapper mapper;
	
	@Transactional
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
		Animal entity = repository.getReferenceById(id);
		mapper.copyInputDTOToEntity(updateDTO, entity);
		entity = repository.save(entity);
		return mapper.convertEntityOutputDTO(entity);
	}
}
