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
import com.fronchak.petshop.domain.services.PetService;

@WebMvcTest(PetController.class)
public abstract class AbstractPetControllerTest {

	protected Long VALID_ID = 0L;
	protected Long INVALID_ID = 1L;
	protected Long DEPENDENT_ID = 2L;
	
	protected MediaType mediaType = MediaType.APPLICATION_JSON;
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@MockBean
	protected PetService service;
	
	protected void assertSuccessAndOutputPetDTO(ResultActions result) throws Exception {
		assertSuccess(result);
		assertOutputPetDTO(result);
	}
	
	protected void assertSuccess(ResultActions result) throws Exception {
		result.andExpect(status().isOk());
	}
	
	protected void assertOutputPetDTO(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.id").value(20L));
		result.andExpect(jsonPath("$.name").value("Mock pet name 0"));
		result.andExpect(jsonPath("$.weightInKg").value(20.0));
		result.andExpect(jsonPath("$.heightInCm").value(120.0));
		result.andExpect(jsonPath("$.animal.id").value(10L));
		result.andExpect(jsonPath("$.animal.name").value("Mock animal name 0"));
		result.andExpect(jsonPath("$.colors[0].id").value(0L));
		result.andExpect(jsonPath("$.colors[0].name").value("Mock color name 0"));
		result.andExpect(jsonPath("$.colors[1].id").value(1L));
		result.andExpect(jsonPath("$.colors[1].name").value("Mock color name 1"));
	}
	
	protected void assertNotFound(ResultActions result) throws Exception {
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.status").value(404));
		result.andExpect(jsonPath("$.error").value("Resource not found"));
	}
	
	protected void assertUnprocessableEntity(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.status").value(422));
		result.andExpect(jsonPath("$.error").value("Validation error"));
	}
	
	protected void assertUnprocessableEntityAndInvalidName(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
		result.andExpect(jsonPath("$.errors[0].message").value("Pet's name cannot be empty"));
	}
	
	protected void assertUprocessableEntityAndInvalidNullWeightInKg(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("weightInKg"));
		result.andExpect(jsonPath("$.errors[0].message").value("Pet's weight must be specified"));
	}
	
	protected void assertUnprocessableEntityAndInvalidTooBigWeightInKg(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("weightInKg"));
		result.andExpect(jsonPath("$.errors[0].message").value("Pet's weight cannot be bigger than 50.0 kg"));
	}
	
	protected void assertUnprocessableEntityAndInvalidNegativeWeightInKg(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("weightInKg"));
		result.andExpect(jsonPath("$.errors[0].message").value("Pet's weight cannot be negative"));
	}
	
	protected void assertUnprocessableEntityAndInvalidIdAnimal(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("idAnimal"));
		result.andExpect(jsonPath("$.errors[0].message").value("Pet's type of animal must be specified"));
	}
	
	protected void assertUnprocessableEntityAndInvalidNullIdColor(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").exists());
		result.andExpect(jsonPath("$.errors[0].message").value("Pet's color cannot be null"));
	}
	
	protected void assertUnprocessableEntityAndInvalidEmptyIdColors(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("idColors"));
		result.andExpect(jsonPath("$.errors[0].message").value("Pet must have at least one color"));
	}
}
