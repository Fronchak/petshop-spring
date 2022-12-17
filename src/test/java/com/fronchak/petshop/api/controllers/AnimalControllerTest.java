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

import com.fronchak.petshop.domain.dtos.animal.OutputAllAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.test.factories.AnimalMocksFactory;

public class AnimalControllerTest extends AbstractAnimalControllerTest {

	@Test
	public void findByIdShouldReturnSuccessAndDTOWhenIdExists() throws Exception {
		OutputAnimalDTO dto = AnimalMocksFactory.mockOutputAnimalDTO();
		when(service.findById(VALID_ID)).thenReturn(dto);
		
		ResultActions result = mockMvc.perform(get("/api/animals/{id}", VALID_ID)
				.accept(mediaType));
		
		assertSuccessAndOutputDTO(result);
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		when(service.findById(INVALID_ID)).thenThrow(ResourceNotFoundException.class);
		
		ResultActions result = mockMvc.perform(get("/api/animals/{id}", INVALID_ID)
				.accept(mediaType));
		
		assertNotFound(result);
	}
	
	@Test
	public void findAllPagedShouldReturnSuccessAndDTOPage() throws Exception {
		Page<OutputAllAnimalDTO> page = AnimalMocksFactory.mockOutputAllAnimalDTOPage();
		when(service.findAllPaged(any(Pageable.class))).thenReturn(page);
		
		ResultActions result = mockMvc.perform(get("/api/animals?page=0&size=10")
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
		
		ResultActions result = mockMvc.perform(delete("/api/animals/{id}", VALID_ID)
				.accept(mediaType));
		
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		doThrow(ResourceNotFoundException.class).when(service).delete(INVALID_ID);
		
		ResultActions result = mockMvc.perform(delete("/api/animals/{id}", INVALID_ID)
				.accept(mediaType));
	
		assertNotFound(result);
	}
	
	@Test
	public void deleteShoudReturnBadRequestWhenIdIsDependent() throws Exception {
		doThrow(DatabaseException.class).when(service).delete(DEPENDENT_ID);
		
		ResultActions result = mockMvc.perform(delete("/api/animals/{id}", DEPENDENT_ID)
				.accept(mediaType));
		
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.status").value(400));
		result.andExpect(jsonPath("$.error").value("Integrity error"));
	}
}
