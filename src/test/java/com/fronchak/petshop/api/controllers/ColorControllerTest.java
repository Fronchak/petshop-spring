package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;

import com.fronchak.petshop.domain.dtos.color.OutputAllColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.test.factories.ColorMocksFactory;

public class ColorControllerTest extends AbstractColorControllerTest {

	@Test
	public void findByIdShouldReturnDTOAndSuccessWhenIdExists() throws Exception {
		OutputColorDTO dto = ColorMocksFactory.mockOutputColorDTO();
		when(service.findById(VALID_ID)).thenReturn(dto);
		
		ResultActions result = mockMvc.perform(get("/api/colors/{id}", VALID_ID)
				.accept(mediaType));
		
		assertSuccessAndOutputColorDTO(result);
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		when(service.findById(INVALID_ID)).thenThrow(ResourceNotFoundException.class);
		
		ResultActions result = mockMvc.perform(get("/api/colors/{id}", INVALID_ID)
				.accept(mediaType));
		
		assertNotFound(result);
	}
	
	@Test
	public void findAllPagedShouldReturnDTOPageAndSuccess() throws Exception {
		Page<OutputAllColorDTO> page = ColorMocksFactory.mockOutputAllColorDTOPage();
		when(service.findAllPaged(any(Pageable.class))).thenReturn(page);
		
		ResultActions result = mockMvc.perform(get("/api/colors")
				.accept(mediaType));
		
		result.andExpect(status().isOk());
		
		result.andExpect(jsonPath("$.content[0].id").value(0L));
		result.andExpect(jsonPath("$.content[0].name").value("Mock name 0"));
		
		result.andExpect(jsonPath("$.content[1].id").value(1L));
		result.andExpect(jsonPath("$.content[1].name").value("Mock name 1"));
		
		result.andExpect(jsonPath("$.content[2].id").value(2L));
		result.andExpect(jsonPath("$.content[2].name").value("Mock name 2"));
	}
	
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
		doNothing().when(service).delete(VALID_ID);
		
		ResultActions result = mockMvc.perform(delete("/api/colors/{id}", VALID_ID)
				.accept(mediaType));
		
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		doThrow(ResourceNotFoundException.class).when(service).delete(INVALID_ID);

		ResultActions result = mockMvc.perform(delete("/api/colors/{id}", INVALID_ID)
				.accept(mediaType));
		
		assertNotFound(result);
	}
	
	@Test
	public void deleteShouldReturnBadRequestWhenIdIsDependent() throws Exception {
		doThrow(DatabaseException.class).when(service).delete(DEPENDENT_ID);
		
		ResultActions result = mockMvc.perform(delete("/api/colors/{id}", DEPENDENT_ID)
				.accept(mediaType));
		
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.status").value(400));
		result.andExpect(jsonPath("$.error").value("Integrity error"));
	}
}
