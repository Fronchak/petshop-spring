package com.fronchak.petshop.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fronchak.petshop.domain.repositories.AnimalRepository;
import com.fronchak.petshop.domain.services.AnimalService;

@WebMvcTest(AnimalController.class)
public abstract class AbstractAnimalControllerTest {

	protected Long VALID_ID = 1L;
	protected Long INVALID_ID = 2L;
	protected Long DEPENDENT_ID = 3L;
	
	protected MediaType mediaType = MediaType.APPLICATION_JSON;
	
	@MockBean
	protected AnimalService service;
	
	@MockBean
	protected AnimalRepository repository;
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	protected void assertNotFound(ResultActions result) throws Exception {
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.status").value(404));
		result.andExpect(jsonPath("$.error").value("Resource not found"));
	}
	
	protected void assertSuccessAndOutputDTO(ResultActions result) throws Exception {
		assertSuccess(result);
		assertOutputDTO(result);
	}
	
	protected void assertSuccess(ResultActions result) throws Exception {
		result.andExpect(status().isOk());
	}
	
	protected void assertOutputDTO(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.id").value(0L));
		result.andExpect(jsonPath("$.name").value("Mock animal name 0"));
		result.andExpect(jsonPath("$.description").value("Mock animal description 0"));
	}
	
	protected void assertCreatedAndOutputDTO(ResultActions result) throws Exception {
		assertCreated(result);
		assertOutputDTO(result);
	}
	
	protected void assertCreated(ResultActions result) throws Exception {
		result.andExpect(status().isCreated());
	}
	
	protected void assertUnprocessableEntityAndInvalidName(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
		result.andExpect(jsonPath("$.errors[0].message").value("Name cannot be empty"));
	}
	
	protected void assertUnprocessableEntity(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.status").value(422));
	}
	
	protected void assertUnprocessableEntityAndInvalidDescription(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("description"));
		result.andExpect(jsonPath("$.errors[0].message").value("Description cannot be empty"));
	}
	
	protected void assertUnprocessableEntityAndInvalidUniqueName(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
		result.andExpect(jsonPath("$.errors[0].message").value("There is another animal with the same name already registered"));
	}
}
