package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;

import com.fronchak.petshop.domain.dtos.pet.OutputAllPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.test.factories.PetMocksFactory;

public class PetControllerTest extends AbstractPetControllerTest {

	@Test
	public void findByIdShouldReturnSuccessAndDTOWhenIdExists() throws Exception {
		OutputPetDTO dto = PetMocksFactory.mockOutputPetDTO();
		
		when(service.findById(VALID_ID)).thenReturn(dto);
		
		ResultActions result = mockMvc.perform(get("/api/pets/{id}", VALID_ID)
				.accept(mediaType));
		
		assertSuccessAndOutputPetDTO(result);
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		when(service.findById(INVALID_ID)).thenThrow(ResourceNotFoundException.class);
		
		ResultActions result = mockMvc.perform(get("/api/pets/{id}", INVALID_ID)
				.accept(mediaType));
		
		assertNotFound(result);
	}
	
	@Test
	public void findAllShouldReturnSuccessAndDTOPage() throws Exception {
		Page<OutputAllPetDTO> page = PetMocksFactory.mockOutputAllPetDTOPage();
		when(service.findAllPaged(any(Pageable.class))).thenReturn(page);
		
		ResultActions result = mockMvc.perform(get("/api/pets?page=0&size=12")
				.accept(mediaType));
		
		assertSuccess(result);
		result.andExpect(jsonPath("$.content[0].id").value(20L));
		result.andExpect(jsonPath("$.content[0].name").value("Mock pet name 0"));
		result.andExpect(jsonPath("$.content[0].animal.id").value(10L));
		result.andExpect(jsonPath("$.content[0].animal.name").value("Mock animal name 0"));
		result.andExpect(jsonPath("$.content[0].colors[0].id").value(0L));
		result.andExpect(jsonPath("$.content[0].colors[0].name").value("Mock color name 0"));
		result.andExpect(jsonPath("$.content[0].colors[1].id").value(1L));
		result.andExpect(jsonPath("$.content[0].colors[1].name").value("Mock color name 1"));
		
		result.andExpect(jsonPath("$.content[1].id").value(21L));
		result.andExpect(jsonPath("$.content[1].name").value("Mock pet name 1"));
		result.andExpect(jsonPath("$.content[1].animal.id").value(11L));
		result.andExpect(jsonPath("$.content[1].animal.name").value("Mock animal name 1"));
		result.andExpect(jsonPath("$.content[1].colors[0].id").value(2L));
		result.andExpect(jsonPath("$.content[1].colors[0].name").value("Mock color name 2"));
		result.andExpect(jsonPath("$.content[1].colors[1].id").value(3L));
		result.andExpect(jsonPath("$.content[1].colors[1].name").value("Mock color name 3"));
	}
	
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
		doNothing().when(service).delete(VALID_ID);
		
		ResultActions result = mockMvc.perform(delete("/api/pets/{id}", VALID_ID)
				.accept(mediaType));
		
		result.andExpect(status().isNoContent());
		verify(service, times(1)).delete(VALID_ID);
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		doThrow(ResourceNotFoundException.class).when(service).delete(INVALID_ID);
		
		ResultActions result = mockMvc.perform(delete("/api/pets/{id}", INVALID_ID)
				.accept(mediaType));
		
		assertNotFound(result);
		verify(service, times(1)).delete(INVALID_ID);
	}
	
	@Test
	public void deleteShouldReturnBadRequestWhenIdIsDependent() throws Exception {
		doThrow(DatabaseException.class).when(service).delete(DEPENDENT_ID);
		
		ResultActions result = mockMvc.perform(delete("/api/pets/{id}", DEPENDENT_ID)
				.accept(mediaType));
		
		result.andExpect(status().isBadRequest());
		verify(service, times(1)).delete(DEPENDENT_ID);
	}
}
