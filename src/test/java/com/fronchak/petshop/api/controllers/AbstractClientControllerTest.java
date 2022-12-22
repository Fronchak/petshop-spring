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
import com.fronchak.petshop.domain.repositories.ClientRepository;
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
	
	@MockBean
	protected ClientRepository repository;
	
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
	
	protected void assertBadRequestAndIntegrityError(ResultActions result) throws Exception {
		assertBadRequest(result);
		result.andExpect(jsonPath("$.error").value("Integrity error"));
	}
	
	protected void assertBadRequest(ResultActions result) throws Exception {
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.status").value(400));
	}
	
	protected void assertCreatedAndOutputDTO(ResultActions result) throws Exception {
		assertCreated(result);
		assertOutputDTO(result);
	}
	
	protected void assertCreated(ResultActions result) throws Exception {
		result.andExpect(status().isCreated());
	}
	
	protected void assertUnprocessableEntityAndInvalidEmptyFirstName(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("firstName"));
		result.andExpect(jsonPath("$.errors[0].message").value("Client's first name cannot be empty"));
	}
	
	protected void assertUnprocessableEntity(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.status").value(422));
		result.andExpect(jsonPath("$.error").value("Validation error"));
	}
	
	protected void assertUnprocessableEntityAndInvalidEmptyLastName(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("lastName"));
		result.andExpect(jsonPath("$.errors[0].message").value("Client's last name cannot be empty"));
	}
	
	protected void assertUnprocessableEntityAndInvalidNullEmail(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("email"));
		result.andExpect(jsonPath("$.errors[0].message").value("Client's email must be specified"));
	}
	
	protected void assertUnprocessableEntityAndInvalidNotWellFormatedEmail(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("email"));
		result.andExpect(jsonPath("$.errors[0].message").value("Invalid email format, please try a valid email"));
	}
	
	protected void assertUnprocessableEntityAndInvalidNullCpf(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("cpf"));
		result.andExpect(jsonPath("$.errors[0].message").value("Client's CPF must be specified"));
	}
	
	protected void assertUnprocessableEntityAndInvalidCpfFormat(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("cpf"));
		result.andExpect(jsonPath("$.errors[0].message").value("Invalid cpf format, please try a valid cpf (only digits)"));
	}
	
	protected void assertUnprocessableEntityAndInvalidNullBirthDate(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("birthDate"));
		result.andExpect(jsonPath("$.errors[0].message").value("Client's birth date must be specified"));		
	}
	
	protected void assertUnprocessableEntityAndInvalidNonPastBirthDate(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("birthDate"));
		result.andExpect(jsonPath("$.errors[0].message").value("Invalid birth date, birth date cannot be a future date"));	
	}
	
	protected void assertUnprocessableEntityAndInvalidRepeatedEmail(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("email"));
		result.andExpect(jsonPath("$.errors[0].message").value("Invalid email, this email was already registered"));
	}
	
	protected void assertUnprocessableEntityAndInvalidRepeatedCpf(ResultActions result) throws Exception {
		assertUnprocessableEntity(result);
		result.andExpect(jsonPath("$.errors[0].fieldName").value("cpf"));
		result.andExpect(jsonPath("$.errors[0].message").value("Invalid cpf, this cpf was already registered"));
	}
} 
