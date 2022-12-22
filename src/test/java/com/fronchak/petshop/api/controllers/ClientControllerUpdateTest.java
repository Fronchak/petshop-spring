package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.dtos.client.UpdateClientDTO;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.test.factories.ClientMocksFactory;

public class ClientControllerUpdateTest extends AbstractClientControllerTest {

	private UpdateClientDTO updateDTO;
	private OutputClientDTO outputDTO;
	private String body;
	private ResultActions result;
	
	@BeforeEach
	public void setUp() {
		updateDTO = ClientMocksFactory.mockUpdateClientDTO();
		outputDTO = ClientMocksFactory.mockOutputClientDTO();
		when(service.update(any(UpdateClientDTO.class), eq(VALID_ID))).thenReturn(outputDTO);
		when(service.update(any(UpdateClientDTO.class), eq(INVALID_ID))).thenThrow(ResourceNotFoundException.class);
	}
	
	private void mapperUpdateDTOAndPerformPostMethod(Long id) throws Exception {
		mapperUpdateDTOToJson();
		performUpdateMethod(id);
	}
	
	private void mapperUpdateDTOToJson() throws Exception {
		body = objectMapper.writeValueAsString(updateDTO);
	}
	
	private void performUpdateMethod(Long id) throws Exception {
		result = mockMvc.perform(put("/api/clients/{id}", id)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
	}
	
	@Test
	public void updateShouldReturnSuccessAndOutputDTOWhenIdExistsAndNoValidationsAreBroken() throws Exception {
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertSuccessAndOutputDTO(result);
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExistAndNoValidationsAreBroken() throws Exception {
		mapperUpdateDTOAndPerformPostMethod(INVALID_ID);
		assertNotFound(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenFirstNameIsBlank() throws Exception {
		updateDTO.setFirstName(" ");
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidEmptyFirstName(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenLastNameIsBlank() throws Exception {
		updateDTO.setLastName(" ");
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidEmptyLastName(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenEmailIsNull() throws Exception {
		updateDTO.setEmail(null);
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNullEmail(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenEmailIsBlank() throws Exception {
		updateDTO.setEmail(" ");
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNotWellFormatedEmail(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenEmailDoesNotContainsAtSymbol() throws Exception {
		updateDTO.setEmail("gabriel.gmail.com");
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNotWellFormatedEmail(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenEmailDoesNotContainsAnyDotAfterAySymbol() throws Exception {
		updateDTO.setEmail("gabriel@gmail");
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNotWellFormatedEmail(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenEmailDoesNotContainsAnyLetterBeforeAtSymbol() throws Exception {
		updateDTO.setEmail("@gmail.com");
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNotWellFormatedEmail(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenCpfIsNull() throws Exception {
		updateDTO.setCpf(null);
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNullCpf(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenCpfIsBlank() throws Exception {
		updateDTO.setCpf(" ");
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidCpfFormat(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenCpfOnlyContainsTenDigits() throws Exception {
		updateDTO.setCpf("0123456789");
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidCpfFormat(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenCpfContainsTwelveDigits() throws Exception {
		updateDTO.setCpf("012345678910");
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidCpfFormat(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenCpfContainsAtLeastOneNonNumericCharacter() throws Exception {
		updateDTO.setCpf("0123456789A");
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidCpfFormat(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenBirthDateIsNull() throws Exception {
		updateDTO.setBirthDate(null);
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNullBirthDate(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenBirthDateIsNotAPastDate() throws Exception {
		updateDTO.setBirthDate(LocalDate.of(2030, 1, 1));
		mapperUpdateDTOAndPerformPostMethod(VALID_ID);
		assertUnprocessableEntityAndInvalidNonPastBirthDate(result);
	}
}
