package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;

import com.fronchak.petshop.domain.dtos.client.OutputAllClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.test.factories.ClientMocksFactory;

public class ClientControllerTest extends AbstractClientControllerTest {

	@Test
	public void findByIdShouldReturnSuccessAndOutputDTOWhenIdExists() throws Exception {
		OutputClientDTO outputDTO = ClientMocksFactory.mockOutputClientDTO();
		
		when(service.findById(VALID_ID)).thenReturn(outputDTO);
		
		ResultActions result = mockMvc.perform(get("/api/clients/{id}", VALID_ID)
				.accept(mediaType));
		
		assertSuccessAndOutputDTO(result);
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExis() throws Exception {
		when(service.findById(INVALID_ID)).thenThrow(ResourceNotFoundException.class);
		ResultActions result = mockMvc.perform(get("/api/clients/{id}", INVALID_ID)
				.accept(mediaType));
		assertNotFound(result);
	}
	
	@Test
	public void findAllPagedShouldReturnSuccessAndOutputAllDTOPage() throws Exception {
		Page<OutputAllClientDTO> page = ClientMocksFactory.mockOutputAllClientDTOPage();
		when(service.findAllPaged(any(Pageable.class))).thenReturn(page);
		
		ResultActions result = mockMvc.perform(get("/api/clients?page=0&size=12")
				.accept(mediaType));
		assertSuccessAndOutputAllDTOPage(result);
	}
}
