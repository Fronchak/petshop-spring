package com.fronchak.petshop.domain.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.entities.Client;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.ClientMapper;
import com.fronchak.petshop.domain.repositories.ClientRepository;
import com.fronchak.petshop.test.factories.ClientMocksFactory;
import com.fronchak.petshop.util.CustomizeAsserts;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

	private Long VALID_ID = 1L;
	private Long INVALID_ID = 2L;
	private Long DEPENDENT_ID = 3L;
	
	@InjectMocks
	private ClientService service;
	
	@Mock
	private ClientRepository repository;
	
	@Mock
	private ClientMapper mapper;
	
	@Test
	public void findByIdShouldReturnDTOWhenIdExists() {
		Client entity = ClientMocksFactory.mockClient();
		OutputClientDTO dto = ClientMocksFactory.mockOutputClientDTO();
		when(repository.findById(VALID_ID)).thenReturn(Optional.of(entity));
		when(mapper.convertEntityToOutputDTO(entity)).thenReturn(dto);
		
		OutputClientDTO result = service.findById(VALID_ID);
		CustomizeAsserts.assertOutputClientDTO(result);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundWhenIdDoesNotExist() {
		when(repository.findById(INVALID_ID)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> service.findById(INVALID_ID));
	}
}
