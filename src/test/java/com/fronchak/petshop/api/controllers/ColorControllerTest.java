package com.fronchak.petshop.api.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fronchak.petshop.api.controllers.ColorController;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.services.ColorService;
import com.fronchak.petshop.test.factories.ColorMocksFactory;

@WebMvcTest(ColorController.class)
public class ColorControllerTest {

	private Long VALID_ID = 1L;
	private Long INVALID_ID = 2L;
	private Long DEPENDENT_ID = 3L;
	
	private MediaType mediaType = MediaType.APPLICATION_JSON;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ColorService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void findByIdShouldReturnDTOAndSuccessWhenIdExists() throws Exception {
		OutputColorDTO dto = ColorMocksFactory.mockOutputColorDTO();
		when(service.findById(VALID_ID)).thenReturn(dto);
		
		ResultActions result = mockMvc.perform(get("/api/colors/{id}", VALID_ID)
				.accept(mediaType));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(0L));
		result.andExpect(jsonPath("$.name").value("Mock name 0"));
		result.andExpect(jsonPath("$.rgb").value("Mock rgb 0"));
		result.andExpect(jsonPath("$.hex").value("Mock hex 0"));
	}
}
