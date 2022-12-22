package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.fronchak.petshop.domain.dtos.client.InsertClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.entities.Client;
import com.fronchak.petshop.test.factories.ClientMocksFactory;

public class ClientControllerSaveTest extends AbstractClientControllerTest {

	private InsertClientDTO insertDTO;
	private OutputClientDTO outputDTO;
	private String body;
	private ResultActions result;
	
	@BeforeEach
	public void setUp() {
		insertDTO = ClientMocksFactory.mockInsertClientDTO();
		outputDTO = ClientMocksFactory.mockOutputClientDTO();
		when(service.save(any(InsertClientDTO.class))).thenReturn(outputDTO);
		when(repository.findByEmail(insertDTO.getEmail())).thenReturn(null);
		when(repository.findByCpf(insertDTO.getCpf())).thenReturn(null);
	}
	
	private void mapperInsertDTOAndPerformPostMethod() throws Exception {
		mapperInsertDTOToJson();
		performPostMethod();
	}
	
	private void mapperInsertDTOToJson() throws Exception {
		body = objectMapper.writeValueAsString(insertDTO);
	}
	
	private void performPostMethod() throws Exception {
		result = mockMvc.perform(post("/api/clients")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
	}
	
	@Test
	public void saveShouldReturnCreatedAndOutputDTOWhenNoValidationsAreBroken() throws Exception {
		mapperInsertDTOAndPerformPostMethod();
		
		assertCreatedAndOutputDTO(result);
		verify(service, times(1)).save(any(InsertClientDTO.class));
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenFirstNameIsEmpty() throws Exception {
		insertDTO.setFirstName(" ");
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidEmptyFirstName(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenLastNameIsBlank() throws Exception {
		insertDTO.setLastName(" ");
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidEmptyLastName(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenEmailIsNull() throws Exception {
		insertDTO.setEmail(null);
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidNullEmail(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenEmailIsBlank() throws Exception {
		insertDTO.setEmail(" ");
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidNotWellFormatedEmail(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenEmailDoesNotContainsAtSymbol() throws Exception {
		insertDTO.setEmail("gabriel.gmail.com");
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidNotWellFormatedEmail(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenEmailDoesNotContainsDotAfterAtSymbol() throws Exception {
		insertDTO.setEmail("gabriel@gmail");
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidNotWellFormatedEmail(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenEmailDoesNotAnyLetterBeforeAtSymbol() throws Exception {
		insertDTO.setEmail("@gmail.com");
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidNotWellFormatedEmail(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenCpfIsNull() throws Exception {
		insertDTO.setCpf(null);
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidNullCpf(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenCpfIsBlank() throws Exception {
		insertDTO.setCpf(" ");
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidCpfFormat(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenCpfOnlyContainsTenDigits() throws Exception {
		insertDTO.setCpf("0123456789");
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidCpfFormat(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenCpfContainsTwelveDigits() throws Exception {
		insertDTO.setCpf("012345678901");
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidCpfFormat(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenCpfContainsAnyCharacter() throws Exception {
		insertDTO.setCpf("0123456789A");
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidCpfFormat(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenBirthDateIsNull() throws Exception {
		insertDTO.setBirthDate(null);
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidNullBirthDate(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenBirthDateIsInTheFuture() throws Exception {
		insertDTO.setBirthDate(LocalDate.of(2030, 1, 15));
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidNonPastBirthDate(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenThereIsAnotherClientWithTheSameEmailRegister() throws Exception {
		when(repository.findByEmail(insertDTO.getEmail())).thenReturn(new Client());
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidRepeatedEmail(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenThereIsAnotherClientWithTheSameCpfRegister() throws Exception {
		when(repository.findByCpf(insertDTO.getCpf())).thenReturn(new Client());
		mapperInsertDTOAndPerformPostMethod();
		assertUnprocessableEntityAndInvalidRepeatedCpf(result);
	}
}
