package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.dtos.pet.UpdatePetDTO;
import com.fronchak.petshop.domain.exceptions.DatabaseReferenceException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.exceptions.ValidationException;
import com.fronchak.petshop.test.factories.PetMocksFactory;

public class PetControllerUpdateTest extends AbstractPetControllerTest {

	private UpdatePetDTO updateDTO;
	private OutputPetDTO outputDTO;
	private String body;
	private ResultActions result;
	
	@BeforeEach
	void setUp() {
		updateDTO = PetMocksFactory.mockUpdatePetDTO();
		outputDTO = PetMocksFactory.mockOutputPetDTO();
		when(service.update(any(UpdatePetDTO.class), eq(VALID_ID))).thenReturn(outputDTO);
		when(service.update(any(UpdatePetDTO.class), eq(INVALID_ID))).thenThrow(ResourceNotFoundException.class);
	}
	
	private void mapperUpdateDTOToJson() throws Exception {
		body = objectMapper.writeValueAsString(updateDTO);
	}
	
	private void performPutMethod(Long id) throws Exception {
		result = mockMvc.perform(put("/api/pets/{id}", id)
				.content(body)
				.accept(mediaType)
				.contentType(mediaType));
	}
	
	@Test
	public void updateShouldReturnSuccessAndIdExistsAndNoValidationsAreBroken() throws Exception {
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertSuccessAndOutputPetDTO(result);
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenNotValidationsAreBrokenButIdDoesNotExist() throws Exception {
		mapperUpdateDTOToJson();
		performPutMethod(INVALID_ID);
		assertNotFound(result);
	}
	
	@Test
	public void updateShouldReturnBadRequestWhenServiceThrowsDatabaseReferenceException() throws Exception {
		when(service.update(any(UpdatePetDTO.class), eq(VALID_ID))).thenThrow(DatabaseReferenceException.class);
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertBadRequestAndDatabaseReferenceException(result);
	}
	
	@Test
	public void updateShouldReturnBadRequestWhenServiceThrowsValidationException() throws Exception {
		when(service.update(any(UpdatePetDTO.class), eq(VALID_ID))).thenThrow(ValidationException.class);
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertBadRequestAndValidationException(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenNameIsBlank() throws Exception {
		updateDTO.setName(" ");
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidName(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenWeightIsNull() throws Exception {
		updateDTO.setWeightInKg(null);
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertUprocessableEntityAndInvalidNullWeightInKg(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenWeightIsBiggerThan50Kg() throws Exception {
		updateDTO.setWeightInKg(50.01);
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidTooBigWeightInKg(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenWeightIsNotPositive() throws Exception {
		updateDTO.setWeightInKg(0.0);
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNegativeWeightInKg(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenHeightIsNull() throws Exception {
		updateDTO.setHeightInCm(null);
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNullHeight(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntiyWhenHeightBiggerThen200cm() throws Exception {
		updateDTO.setHeightInCm(200.01);
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidGreaterThan200Height(result);	
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenHeightIsNotPositive() throws Exception {
		updateDTO.setHeightInCm(0.0);
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNonPositiveHeight(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenIdAnimalIsNull() throws Exception {
		updateDTO.setIdAnimal(null);
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidIdAnimal(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenAtLeastOneIdColorIsNull() throws Exception {
		updateDTO.addIdColor(null);
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNullIdColor(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenIdColorsIsEmpty() throws Exception {
		updateDTO.getIdColors().clear();
		mapperUpdateDTOToJson();
		performPutMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidEmptyIdColors(result);
	}
}
