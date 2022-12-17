package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.fronchak.petshop.domain.dtos.animal.InsertAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.test.factories.AnimalMocksFactory;

public class AnimalControllerSaveTest extends AbstractAnimalControllerTest {

	private InsertAnimalDTO insertDTO;
	private OutputAnimalDTO outputDTO;
	private Animal entity;
	private String body;
	
	@BeforeEach
	void setUp() throws Exception {
		insertDTO = AnimalMocksFactory.mockInsertAnimalDTO();
		outputDTO = AnimalMocksFactory.mockOutputAnimalDTO();
		
		when(repository.findByName(insertDTO.getName())).thenReturn(null);
		when(service.save(any(InsertAnimalDTO.class))).thenReturn(outputDTO);
		
		body = objectMapper.writeValueAsString(insertDTO);
	}
	
	@Test
	public void saveShouldReturnDTOWhenNoValidationsAreBroken() throws Exception {
		ResultActions result = mockMvc.perform(post("/api/animals")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertCreatedAndOutputDTO(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenNameIsBlank() throws Exception {
		insertDTO.setName(" ");
		body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/animals")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndInvalidName(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenDescriptionInBlank() throws Exception {
		insertDTO.setDescription(" ");
		body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/animals")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndInvalidDescription(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenNameIsDuplicate() throws Exception {
		entity = AnimalMocksFactory.mockAnimal();
		when(repository.findByName(insertDTO.getName())).thenReturn(entity);
		
		ResultActions result = mockMvc.perform(post("/api/animals")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndInvalidUniqueName(result);
	}
}
