package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.dtos.color.UpdateColorDTO;
import com.fronchak.petshop.domain.entities.Color;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.test.factories.ColorMocksFactory;

public class ColorControllerUpdateTest extends AbstractColorControllerTest {
	
	private MediaType mediaType = MediaType.APPLICATION_JSON;
	
	private UpdateColorDTO updateDTO;
	private OutputColorDTO dto;
	private Color entity;
	
	private String body;

	@BeforeEach
	void setUp() throws JsonProcessingException {
		updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		dto = ColorMocksFactory.mockOutputColorDTO();		
		body = objectMapper.writeValueAsString(updateDTO);
		
		when(repository.findByName(updateDTO.getName())).thenReturn(null);
		when(repository.findByHex(updateDTO.getHex())).thenReturn(null);
		when(repository.findByRgb(updateDTO.getRgb())).thenReturn(null);
		when(service.update(any(UpdateColorDTO.class), eq(VALID_ID))).thenReturn(dto);
	}
	
	@Test
	public void updateShouldReturnDTOAndSuccessWhenIdExistsAndThereIsNoEntityWithTheSameValues() throws Exception {
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.accept(mediaType)
				.content(body)
				.contentType(mediaType));
		
		assertSuccessAndOutputColorDTO(result);
	}
	
	@Test
	public void updateShouldReturnDTOAndSuccessWhenIdExistsAndEntityWithTheSameNameIsEntityBeenUpdate() throws Exception {
		entity = ColorMocksFactory.mockColor(1);
		when(repository.findByName(updateDTO.getName())).thenReturn(entity);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.accept(mediaType)
				.content(body)
				.contentType(mediaType));
		
		assertSuccessAndOutputColorDTO(result);
	}
	
	@Test
	public void updateShouldReturnDTOAndSuccessWhenIdExistsAndEntityWithTheSameHexIsEntityBeenUpdate() throws Exception {
		entity = ColorMocksFactory.mockColor(1);
		when(repository.findByHex(updateDTO.getHex())).thenReturn(entity);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.accept(mediaType)
				.content(body)
				.contentType(mediaType));
		
		assertSuccessAndOutputColorDTO(result);
	}
	
	@Test
	public void updateShouldReturnDTOAndSuccessWhenIdExistsAndEntityWithTheSameRgbIsEntityBeenUpdate() throws Exception {
		entity = ColorMocksFactory.mockColor(1);
		when(repository.findByRgb(updateDTO.getRgb())).thenReturn(entity);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.accept(mediaType)
				.content(body)
				.contentType(mediaType));
		
		assertSuccessAndOutputColorDTO(result);
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		when(service.update(any(UpdateColorDTO.class), eq(INVALID_ID))).thenThrow(ResourceNotFoundException.class);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", INVALID_ID)
				.accept(mediaType)
				.content(body)
				.contentType(mediaType));
		
		assertNotFound(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenNameIsBlank() throws Exception {
		updateDTO.setName("  ");
		body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocesableEntityAndFieldNameInvalid(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenRgbIsBlank() throws Exception {
		updateDTO.setRgb("  ");
		String body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndFieldRgbInvalid(result);
	}

	@Test
	public void updateShouldReturnUnprocessableEntityWhenHexIsBlank() throws Exception {
		updateDTO.setHex("  ");
		String body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));	
		
		assertUnprocessableEntityAndFieldHexInvalid(result);
	}

	@Test
	public void updateShouldReturnUnprocessableEntityWhenNameIsNotUnique() throws Exception {
		entity = ColorMocksFactory.mockColor();
		when(repository.findByName(updateDTO.getName())).thenReturn(entity);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocesableEntityAndNameNotUnique(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWheRgbIsNotUnique() throws Exception {
		entity = ColorMocksFactory.mockColor();
		when(repository.findByRgb(updateDTO.getRgb())).thenReturn(entity);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocesableEntityAndRgbNotUnique(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWheHexIsNotUnique() throws Exception {
		entity = ColorMocksFactory.mockColor();
		when(repository.findByHex(updateDTO.getHex())).thenReturn(entity);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocesableEntityAndHexNotUnique(result);
	}
}
