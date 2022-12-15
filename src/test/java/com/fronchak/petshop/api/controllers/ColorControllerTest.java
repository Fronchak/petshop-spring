package com.fronchak.petshop.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fronchak.petshop.domain.dtos.color.InsertColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputAllColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.dtos.color.UpdateColorDTO;
import com.fronchak.petshop.domain.entities.Color;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.repositories.ColorRepository;
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
	
	@MockBean
	private ColorRepository repository;
	
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
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		when(service.findById(INVALID_ID)).thenThrow(ResourceNotFoundException.class);
		
		ResultActions result = mockMvc.perform(get("/api/colors/{id}", INVALID_ID)
				.accept(mediaType));
		
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.status").value(404));
		result.andExpect(jsonPath("$.error").value("Resource not found"));
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
		
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.status").value(404));
		result.andExpect(jsonPath("$.error").value("Resource not found"));
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
	
	@Test
	public void saveShouldReturnDTOAndCreated() throws Exception {
		InsertColorDTO insertDTO = ColorMocksFactory.mockInsertColorDTO();
		OutputColorDTO dto = ColorMocksFactory.mockOutputColorDTO();
		String body = objectMapper.writeValueAsString(insertDTO);
		when(service.save(any(InsertColorDTO.class))).thenReturn(dto);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").value(0L));
		result.andExpect(jsonPath("$.name").value("Mock name 0"));
		result.andExpect(jsonPath("$.hex").value("Mock hex 0"));
		result.andExpect(jsonPath("$.rgb").value("Mock rgb 0"));
	}
	
	@Test
	public void updateShouldReturnDTOAndSuccessWhenIdExistsAndThereIsNoEntityWithTheSameValues() throws Exception {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		OutputColorDTO dto = ColorMocksFactory.mockOutputColorDTO();
		
		when(repository.findByName(updateDTO.getName())).thenReturn(null);
		when(repository.findByHex(updateDTO.getHex())).thenReturn(null);
		when(repository.findByRgb(updateDTO.getRgb())).thenReturn(null);
		when(service.update(any(UpdateColorDTO.class), eq(VALID_ID))).thenReturn(dto);
		
		String body = objectMapper.writeValueAsString(updateDTO);
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.accept(mediaType)
				.content(body)
				.contentType(mediaType));
		assertOutputColorDTO(result);
	}
	
	private void assertOutputColorDTO(ResultActions result) throws Exception {
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(0L));
		result.andExpect(jsonPath("$.name").value("Mock name 0"));
		result.andExpect(jsonPath("$.hex").value("Mock hex 0"));
		result.andExpect(jsonPath("$.rgb").value("Mock rgb 0"));
	}
	
	@Test
	public void updateShouldReturnDTOAndSuccessWhenIdExistsAndEntityWithTheSameNameIsEntityBeenUpdate() throws Exception {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		OutputColorDTO dto = ColorMocksFactory.mockOutputColorDTO();
		Color entity = ColorMocksFactory.mockColor(1);
		
		when(repository.findByName(updateDTO.getName())).thenReturn(entity);
		when(repository.findByHex(updateDTO.getHex())).thenReturn(null);
		when(repository.findByRgb(updateDTO.getRgb())).thenReturn(null);
		when(service.update(any(UpdateColorDTO.class), eq(VALID_ID))).thenReturn(dto);
		
		String body = objectMapper.writeValueAsString(updateDTO);
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.accept(mediaType)
				.content(body)
				.contentType(mediaType));
		assertOutputColorDTO(result);
	}
	
	@Test
	public void updateShouldReturnDTOAndSuccessWhenIdExistsAndEntityWithTheSameHexIsEntityBeenUpdate() throws Exception {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		OutputColorDTO dto = ColorMocksFactory.mockOutputColorDTO();
		Color entity = ColorMocksFactory.mockColor(1);
		
		when(repository.findByName(updateDTO.getName())).thenReturn(null);
		when(repository.findByHex(updateDTO.getHex())).thenReturn(entity);
		when(repository.findByRgb(updateDTO.getRgb())).thenReturn(null);
		when(service.update(any(UpdateColorDTO.class), eq(VALID_ID))).thenReturn(dto);
		
		String body = objectMapper.writeValueAsString(updateDTO);
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.accept(mediaType)
				.content(body)
				.contentType(mediaType));
		assertOutputColorDTO(result);
	}
	
	@Test
	public void updateShouldReturnDTOAndSuccessWhenIdExistsAndEntityWithTheSameRgbIsEntityBeenUpdate() throws Exception {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		OutputColorDTO dto = ColorMocksFactory.mockOutputColorDTO();
		Color entity = ColorMocksFactory.mockColor(1);
		
		when(repository.findByName(updateDTO.getName())).thenReturn(null);
		when(repository.findByHex(updateDTO.getHex())).thenReturn(null);
		when(repository.findByRgb(updateDTO.getRgb())).thenReturn(entity);
		when(service.update(any(UpdateColorDTO.class), eq(VALID_ID))).thenReturn(dto);
		
		String body = objectMapper.writeValueAsString(updateDTO);
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.accept(mediaType)
				.content(body)
				.contentType(mediaType));
		assertOutputColorDTO(result);
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		when(service.update(any(UpdateColorDTO.class), eq(INVALID_ID))).thenThrow(ResourceNotFoundException.class);
		
		String body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", INVALID_ID)
				.accept(mediaType)
				.content(body)
				.contentType(mediaType));
		
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.status").value(404));
		result.andExpect(jsonPath("$.error").value("Resource not found"));
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenNameIsNull() throws Exception {
		InsertColorDTO insertDTO = ColorMocksFactory.mockInsertColorDTO();
		insertDTO.setName(null);
		
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnproccesableEntityAndFieldNameInvalid(result);
	}
	
	private void assertUnproccesableEntityAndFieldNameInvalid(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
		result.andExpect(jsonPath("$.errors[0].message").value("Name cannot be empty"));
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenNameIsBlank() throws Exception {
		InsertColorDTO insertDTO = ColorMocksFactory.mockInsertColorDTO();
		insertDTO.setName("  ");
		
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnproccesableEntityAndFieldNameInvalid(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenRgbIsBlank() throws Exception {
		InsertColorDTO insertDTO = ColorMocksFactory.mockInsertColorDTO();
		insertDTO.setRgb("  ");
		
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndFieldRgbInvalid(result);
	}
	
	private void assertUnprocessableEntityAndFieldRgbInvalid(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("rgb"));
		result.andExpect(jsonPath("$.errors[0].message").value("Rgb cannot be empty"));
	}
	
	@Test
	public void saveShouldReturnUnprecessableEntityWhenHexIsBlank() throws Exception {
		InsertColorDTO insertDTO = ColorMocksFactory.mockInsertColorDTO();
		insertDTO.setHex("  ");
		
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndFieldHexInvalid(result);
	}
	
	private void assertUnprocessableEntityAndFieldHexInvalid(ResultActions result) throws Exception {
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("hex"));
		result.andExpect(jsonPath("$.errors[0].message").value("Hex cannot be empty"));
	}
	 
	@Test
	public void updateShouldReturnUnprocessableEntityWhenNameIsBlank() throws Exception {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		updateDTO.setName("  ");
		
		String body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnproccesableEntityAndFieldNameInvalid(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenRgbIsBlank() throws Exception {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		updateDTO.setRgb("  ");
		
		String body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndFieldRgbInvalid(result);
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenHexIsBlank() throws Exception {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		updateDTO.setHex("  ");
		
		String body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		assertUnprocessableEntityAndFieldHexInvalid(result);
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenNameIsNotUnique() throws Exception {
		InsertColorDTO insertDTO = ColorMocksFactory.mockInsertColorDTO();
		when(repository.findByName(insertDTO.getName())).thenReturn(new Color());
		
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].message").value("There is another color with the same name saved"));
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenRgbIsNotUnique() throws Exception {
		InsertColorDTO insertDTO = ColorMocksFactory.mockInsertColorDTO();
		when(repository.findByRgb(insertDTO.getRgb())).thenReturn(new Color());
		
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].message").value("There is another color with the same rgb saved"));
	}
	
	@Test
	public void saveShouldReturnUnprocessableEntityWhenHexIsNotUnique() throws Exception {
		InsertColorDTO insertDTO = ColorMocksFactory.mockInsertColorDTO();
		when(repository.findByHex(insertDTO.getHex())).thenReturn(new Color());
		
		String body = objectMapper.writeValueAsString(insertDTO);
		
		ResultActions result = mockMvc.perform(post("/api/colors")
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].message").value("There is another color with the same hex saved"));
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenNameInNotUnique() throws Exception {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		Color color = ColorMocksFactory.mockColor();
		when(repository.findByName(updateDTO.getName())).thenReturn(color);
		
		String body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.status").value(422));
		result.andExpect(jsonPath("$.errors[0].message").value("There is another color with the same name saved"));
	}
	
	@Test
	public void updateShouldReturnSuccessWhenA() throws Exception {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO();
		OutputColorDTO dto = ColorMocksFactory.mockOutputColorDTO();
		Color color = ColorMocksFactory.mockColor(1);
		
		when(repository.findByName(updateDTO.getName())).thenReturn(color);
		when(service.update(any(UpdateColorDTO.class), eq(VALID_ID))).thenReturn(dto);
		
		String body = objectMapper.writeValueAsString(updateDTO);
		
		ResultActions result = mockMvc.perform(put("/api/colors/{id}", VALID_ID)
				.content(body)
				.contentType(mediaType)
				.accept(mediaType));
		
		result.andExpect(status().isOk());
	}
}
