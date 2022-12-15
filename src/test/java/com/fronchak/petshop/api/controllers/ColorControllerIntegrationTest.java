package com.fronchak.petshop.api.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fronchak.petshop.domain.dtos.color.InsertColorDTO;
import com.fronchak.petshop.test.factories.ColorMocksFactory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ColorControllerIntegrationTest {

	private Long VALID_ID = 1L;
	private Long INVALID_ID = 1000L;
	
	private MediaType mediaType = MediaType.APPLICATION_JSON;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenNameIsNotUnique() throws Exception {
		InsertColorDTO insertDTO = ColorMocksFactory.mockInsertColorDTO();
		insertDTO.setName("Red");
		
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		result.andExpect(status().isUnprocessableEntity());
	}
}
