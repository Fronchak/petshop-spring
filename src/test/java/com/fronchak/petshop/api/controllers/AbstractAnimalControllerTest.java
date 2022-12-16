package com.fronchak.petshop.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fronchak.petshop.domain.services.AnimalService;

@WebMvcTest(AnimalController.class)
public abstract class AbstractAnimalControllerTest {

	protected Long VALID_ID = 1L;
	protected Long INVALID_ID = 2L;
	protected Long DEPENDENT_ID = 3L;
	
	protected MediaType mediaType = MediaType.APPLICATION_JSON;
	
	@MockBean
	protected AnimalService service;
	
	@Autowired
	protected MockMvc mockMvc;
	
	protected ObjectMapper objectMapper;
}
