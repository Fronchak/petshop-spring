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
import com.fronchak.petshop.domain.repositories.ColorRepository;
import com.fronchak.petshop.domain.services.ColorService;

@WebMvcTest(ColorController.class)
public class AbstractColorControllerTest {

	protected Long VALID_ID = 1L;
	protected Long INVALID_ID = 2L;
	protected Long DEPENDENT_ID = 3L;
	
	protected MediaType mediaType = MediaType.APPLICATION_JSON;
	
	@Autowired
	protected MockMvc mockMvc;
	
	@MockBean
	protected ColorService service;
	
	@MockBean
	protected ColorRepository repository;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	protected void assertSuccessAndOutputColorDTO(ResultActions result) throws Exception {
		result.andExpect(status().isOk());
		assertOutputColorDTO(result);
	}
	
	protected void assertOutputColorDTO(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.id").value(0L));
		result.andExpect(jsonPath("$.name").value("Mock name 0"));
		result.andExpect(jsonPath("$.hex").value("Mock hex 0"));
		result.andExpect(jsonPath("$.rgb").value("Mock rgb 0"));
	}
	
	protected void assertUnprocessableEntityAndFieldRgbInvalid(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("rgb"));
		result.andExpect(jsonPath("$.errors[0].message").value("Rgb cannot be empty"));
	}
	
	protected void assertUnprocessableEntityAndFieldHexInvalid(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("hex"));
		result.andExpect(jsonPath("$.errors[0].message").value("Hex cannot be empty"));
	}
	
	protected void assertUnprocesableEntityAndFieldNameInvalid(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
		result.andExpect(jsonPath("$.errors[0].message").value("Name cannot be empty"));
	}
	
	protected void assertNotFound(ResultActions result) throws Exception {
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.status").value(404));
		result.andExpect(jsonPath("$.error").value("Resource not found"));
	}
	
	protected void assertUnprocesableEntityAndNameNotUnique(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.status").value(422));
		result.andExpect(jsonPath("$.errors[0].message").value("There is another color with the same name saved"));
	}
	
	protected void assertUnprocesableEntityAndHexNotUnique(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.status").value(422));
		result.andExpect(jsonPath("$.errors[0].message").value("There is another color with the same hex saved"));
	}
	
	protected void assertUnprocesableEntityAndRgbNotUnique(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.status").value(422));
		result.andExpect(jsonPath("$.errors[0].message").value("There is another color with the same rgb saved"));
	}
}
