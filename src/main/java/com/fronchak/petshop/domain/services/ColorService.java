package com.fronchak.petshop.domain.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fronchak.petshop.domain.dtos.color.InsertColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputAllColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.dtos.color.UpdateColorDTO;
import com.fronchak.petshop.domain.entities.Color;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.ColorMapper;
import com.fronchak.petshop.domain.repositories.ColorRepository;

@Service
public class ColorService {

	@Autowired
	private ColorRepository repository;
	
	@Autowired
	private ColorMapper mapper;
	
	@Transactional(readOnly = true)
	public OutputColorDTO findById(Long id) {
		Color entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Color not found by ID: " + id));
		return mapper.convertEntityToOutputDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Page<OutputAllColorDTO> findAllPaged(Pageable pageable) {
		Page<Color> entityPage = repository.findAll(pageable);
		return mapper.convertEntityPageToOutputAllDTOPage(entityPage);
	}
	
	@Transactional
	public OutputColorDTO save(InsertColorDTO dto) {
		Color entity = new Color();
		mapper.copyInputDTOToEntity(dto, entity);
		entity = repository.save(entity);
		return mapper.convertEntityToOutputDTO(entity);
	}
	
	@Transactional
	public OutputColorDTO update(UpdateColorDTO dto, Long id) {
		try {
			Color entity = repository.getReferenceById(id);
			mapper.copyInputDTOToEntity(dto, entity);
			entity = repository.save(entity);
			return mapper.convertEntityToOutputDTO(entity);			
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Color not found by ID: " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);			
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Color not found by ID: " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity exception");
		}
	}
}
