package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fronchak.petshop.domain.dtos.color.InsertColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.entities.Color;
import com.fronchak.petshop.test.factories.ColorMocksFactory;

public class ColorControllerSaveTest extends AbstractColorControllerTest {

	private InsertColorDTO insertDTO;
	private OutputColorDTO dto;
	private String body;
	
	@BeforeEach
	void setUp() throws JsonProcessingException {
		insertDTO = ColorMocksFactory.mockInsertColorDTO();
		dto = ColorMocksFactory.mockOutputColorDTO();
		body = objectMapper.writeValueAsString(insertDTO);
		
		when(service.save(any(InsertColorDTO.class))).thenReturn(dto);
	}
	
	@Test
	public void saveShouldReturnDTOAndCreated() throws Exception {
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		result.andExpect(status().isCreated());
		assertOutputColorDTO(result);
	}

	@Test
	public void saveShouldReturnUnprocessableEntityWhenNameIsNull() throws Exception {
		insertDTO.setName(null);
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocesableEntityAndFieldNameInvalid(result);
	}

	@Test
	public void saveShouldReturnUnprocessableEntityWhenNameIsBlank() throws Exception {
		insertDTO.setName("  ");
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocesableEntityAndFieldNameInvalid(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenRgbIsBlank() throws Exception {
		insertDTO.setRgb("  ");
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndFieldRgbInvalid(result);
	}

	@Test
	public void saveShouldReturnUnprecessableEntityWhenHexIsBlank() throws Exception {
		insertDTO.setHex("  ");
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndFieldHexInvalid(result);
	}

	@Test
	public void saveShouldReturnUnprocessableEntityWhenNameIsNotUnique() throws Exception {
		when(repository.findByName(insertDTO.getName())).thenReturn(new Color());
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocesableEntityAndNameNotUnique(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenRgbIsNotUnique() throws Exception {
		when(repository.findByRgb(insertDTO.getRgb())).thenReturn(new Color());

		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocesableEntityAndRgbNotUnique(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenHexIsNotUnique() throws Exception {
		when(repository.findByHex(insertDTO.getHex())).thenReturn(new Color());

		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocesableEntityAndHexNotUnique(result);
	}
}
