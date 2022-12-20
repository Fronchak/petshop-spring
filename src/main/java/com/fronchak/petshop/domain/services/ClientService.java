package com.fronchak.petshop.domain.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fronchak.petshop.domain.dtos.client.InsertClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputAllClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.dtos.client.UpdateClientDTO;
import com.fronchak.petshop.domain.entities.Client;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.ClientMapper;
import com.fronchak.petshop.domain.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Autowired
	private ClientMapper mapper;
	
	@Transactional(readOnly = true)
	public OutputClientDTO findById(Long id) {
		Client entity = repository.findById(id)
				.orElseThrow(() -> 	new ResourceNotFoundException("Client", id));
		return mapper.convertEntityToOutputDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Page<OutputAllClientDTO> findAllPaged(Pageable pageable) {
		Page<Client> page = repository.findAll(pageable);
		return mapper.convertEntityPageToOutputAllClientDTO(page);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);	
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Client", id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Database integrity error, you can't delete this client");
		}
	}
	
	@Transactional
	public OutputClientDTO save(InsertClientDTO dto) {
		Client entity = new Client();
		mapper.copyInputDTOToEntity(dto, entity);
		entity = repository.save(entity);
		return mapper.convertEntityToOutputDTO(entity);
	}
	
	@Transactional
	public OutputClientDTO update(UpdateClientDTO dto, Long id) {
		try {
			Client entity = repository.getReferenceById(id);
			mapper.copyInputDTOToEntity(dto, entity);
			entity = repository.save(entity);
			return mapper.convertEntityToOutputDTO(entity);			
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Client", id);
		}
	}
}
