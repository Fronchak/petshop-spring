package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.fronchak.petshop.domain.dtos.pet.InsertPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.exceptions.DatabaseReferenceException;
import com.fronchak.petshop.domain.exceptions.ValidationException;
import com.fronchak.petshop.test.factories.PetMocksFactory;

public class PetControllerSaveTest extends AbstractPetControllerTest {

	private InsertPetDTO insertDTO;
	private OutputPetDTO outputDTO;
	private String body;
	private ResultActions result;
	
	@BeforeEach
	public void setUp() throws Exception {
		insertDTO = PetMocksFactory.mockInsertPetDTO();
		outputDTO = PetMocksFactory.mockOutputPetDTO();		
		when(service.save(any(InsertPetDTO.class))).thenReturn(outputDTO);
	}
	
	private void mapperInsertDTOToJson() throws Exception {
		body = objectMapper.writeValueAsString(insertDTO);
	}
	
	private void performPostMethod() throws Exception {
		result = mockMvc.perform(post("/api/pets")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
	}
	
	@Test
	public void saveShouldReturnCreatedAndDTOWhenNoValidationsAreBroken() throws Exception {
		mapperInsertDTOToJson();
		performPostMethod();
		result.andExpect(status().isCreated());
		assertOutputPetDTO(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenNameIsBlank() throws Exception {
		insertDTO.setName(" ");
		mapperInsertDTOToJson();
		performPostMethod();
		assertUnprocessableEntityAndInvalidName(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenWeightIsNull() throws Exception {
		insertDTO.setWeightInKg(null);
		mapperInsertDTOToJson();
		performPostMethod();
		assertUprocessableEntityAndInvalidNullWeightInKg(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenWeightIsBiggerThan50() throws Exception {
		insertDTO.setWeightInKg(51.0);
		mapperInsertDTOToJson();
		performPostMethod();
		assertUnprocessableEntityAndInvalidTooBigWeightInKg(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenWeightIsNotPositive() throws Exception {
		insertDTO.setWeightInKg(0.0);
		mapperInsertDTOToJson();
		performPostMethod();
		assertUnprocessableEntityAndInvalidNegativeWeightInKg(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenIdAnimalIsNull() throws Exception {
		insertDTO.setIdAnimal(null);
		mapperInsertDTOToJson();
		performPostMethod();
		assertUnprocessableEntityAndInvalidIdAnimal(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenAtLeastOneIdColorIsNull() throws Exception {
		insertDTO.getIdColors().add(null);
		mapperInsertDTOToJson();
		performPostMethod();
		assertUnprocessableEntityAndInvalidNullIdColor(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenListOfIdColorsIsEmpty() throws Exception {
		insertDTO.getIdColors().clear();
		mapperInsertDTOToJson();
		performPostMethod();
		assertUnprocessableEntityAndInvalidEmptyIdColors(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenHeightIsNull() throws Exception {
		insertDTO.setHeightInCm(null);
		mapperInsertDTOToJson();
		performPostMethod();
		assertUnprocessableEntityAndInvalidNullHeight(result);
	}
	
	@Test
	public void assertUnprocessableEntityAndInvalidNonPositiveHeight() throws Exception {
		insertDTO.setHeightInCm(0.0);
		mapperInsertDTOToJson();
		performPostMethod();
		assertUnprocessableEntityAndInvalidNonPositiveHeight(result);
	}
	
	@Test
	public void assertUnprocessableEntityAndInvalidTooBigHeight() throws Exception {
		insertDTO.setHeightInCm(200.01);
		mapperInsertDTOToJson();
		performPostMethod();
		assertUnprocessableEntityAndInvalidGreaterThan200Height(result);
	}
	
	@Test
	public void assertUnprocessableEntityWhenIdClientIsNull() throws Exception {
		insertDTO.setIdClient(null);
		mapperInsertDTOToJson();
		performPostMethod();
		assertUnprocessableEntityAndInvalidNullIdClient(result);
	}
	
	@Test
	public void saveShouldReturnBadRequestWhenServiceThrowDatabaseReferenceException() throws Exception {
		when(service.save(any(InsertPetDTO.class))).thenThrow(DatabaseReferenceException.class);
		mapperInsertDTOToJson();
		performPostMethod();
		assertBadRequestAndDatabaseReferenceException(result);
	}
	
	@Test
	public void saveShouldReturnBadRequestWhenServiceThrowValidationException() throws Exception {
		when(service.save(any(InsertPetDTO.class))).thenThrow(ValidationException.class);
		mapperInsertDTOToJson();
		performPostMethod();
		assertBadRequestAndValidationException(result);
	}
}
