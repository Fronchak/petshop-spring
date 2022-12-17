package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.UpdateAnimalDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.test.factories.AnimalMocksFactory;

public class AnimalControllerUpdateTest extends AbstractAnimalControllerTest {

	private UpdateAnimalDTO updateDTO;
	private OutputAnimalDTO outputDTO;
	private Animal entity;
	private String body;
	
	@BeforeEach
	void setUp() throws Exception {
		updateDTO = AnimalMocksFactory.mockUpdateAnimalDTO();
		outputDTO = AnimalMocksFactory.mockOutputAnimalDTO();
		
		when(repository.findByName(updateDTO.getName())).thenReturn(null);
		when(service.update(any(UpdateAnimalDTO.class), eq(VALID_ID))).thenReturn(outputDTO);
		
		body = objectMapper.writeValueAsString(updateDTO);
	}
	
	@Test
	public void updateShouldReturnSuccessAndDTOWhenNoValidationsAreBroken() throws Exception {
		ResultActions result = mockMvc.perform(put("/api/animals/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertSuccessAndOutputDTO(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenNameIsBlank() throws Exception {
		updateDTO.setName(" ");
		body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/animals/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndInvalidName(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenDescriptionIsBlank() throws Exception {
		updateDTO.setDescription(" ");
		body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/animals/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndInvalidDescription(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenNameIsDuplicate() throws Exception {
		entity = AnimalMocksFactory.mockAnimal();
		when(repository.findByName(updateDTO.getName())).thenReturn(entity);
		
		ResultActions result = mockMvc.perform(put("/api/animals/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
	
		assertUnprocessableEntityAndInvalidUniqueName(result);
	}
	
	@Test
	public void updateShouldReturnSuccessAndDTOWhenEntityWithTheSameNameIsEntityBeenUpdated() throws Exception {
		entity = AnimalMocksFactory.mockAnimal();
		entity.setId(1L);
		when(repository.findByName(updateDTO.getName())).thenReturn(entity);
		
		ResultActions result = mockMvc.perform(put("/api/animals/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertSuccessAndOutputDTO(result);
		verify(repository, times(1)).findByName(updateDTO.getName());
	}
}
