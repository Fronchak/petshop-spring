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
import com.fronchak.petshop.domain.services.ClientService;

@WebMvcTest(ClientController.class)
public class AbstractClientControllerTest {

	protected Long VALID_ID = 0L;
	protected Long INVALID_ID = 1L;
	protected Long DEPENDENT_ID = 2L;
	
	protected MediaType mediaType = MediaType.APPLICATION_JSON;
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@MockBean
	protected ClientService service;
	
	protected void assertSuccessAndOutputDTO(ResultActions result) throws Exception {
		assertSuccess(result);
		assertOutputDTO(result);
	}
	
	protected void assertSuccess(ResultActions result) throws Exception {
		result.andExpect(status().isOk());
	}
	
	protected void assertOutputDTO(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.id").value(30L));
		result.andExpect(jsonPath("$.name").value("Mock client firstName 0 Mock client lastName 0"));
		result.andExpect(jsonPath("$.email").value("MockClientEmail0@gmail.com"));

		result.andExpect(jsonPath(".pets[0].id").value(20));
		result.andExpect(jsonPath(".pets[0].petName").value("Mock pet name 0"));
		result.andExpect(jsonPath(".pets[0].animalName").value("Mock animal name 0"));
	
		result.andExpect(jsonPath(".pets[1].id").value(21));
		result.andExpect(jsonPath(".pets[1].petName").value("Mock pet name 1"));
		result.andExpect(jsonPath(".pets[1].animalName").value("Mock animal name 1"));
	}
	
	protected void assertNotFound(ResultActions result) throws Exception {
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.status").value(404));
		result.andExpect(jsonPath("$.error").value("Resource not found"));
	}
	
	protected void assertSuccessAndOutputAllDTOPage(ResultActions result) throws Exception {
		assertSuccess(result);
		result.andExpect(jsonPath("$.content[0].id").value(30));
		result.andExpect(jsonPath("$.content[0].name").value("Mock client firstName 0 Mock client lastName 0"));
		result.andExpect(jsonPath("$.content[0].email").value("MockClientEmail0@gmail.com"));
		
		result.andExpect(jsonPath("$.content[1].id").value(31));
		result.andExpect(jsonPath("$.content[1].name").value("Mock client firstName 1 Mock client lastName 1"));
		result.andExpect(jsonPath("$.content[1].email").value("MockClientEmail1@gmail.com"));
		
		result.andExpect(jsonPath("$.content[2].id").value(32));
		result.andExpect(jsonPath("$.content[2].name").value("Mock client firstName 2 Mock client lastName 2"));
		result.andExpect(jsonPath("$.content[2].email").value("MockClientEmail2@gmail.com"));
	}
}
