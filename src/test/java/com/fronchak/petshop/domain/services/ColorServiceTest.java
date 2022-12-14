package com.fronchak.petshop.domain.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.dtos.color.InsertColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputAllColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.dtos.color.UpdateColorDTO;
import com.fronchak.petshop.domain.entities.Color;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.ColorMapper;
import com.fronchak.petshop.domain.repositories.ColorRepository;
import com.fronchak.petshop.test.factories.ColorMocksFactory;
import com.fronchak.petshop.util.CustomizeAsserts;

@ExtendWith(SpringExtension.class)
public class ColorServiceTest {
	
	private Long VALID_ID = 1L;
	private Long INVALID_ID = 2L;
	private Long DEPENDENT_ID = 3L;
	
	@InjectMocks
	private ColorService service;

	@Mock
	private ColorRepository repository;
	
	@Mock
	private ColorMapper mapper;
	
	@Test
	public void findByIdShouldReturnDTOWhenIdExists() {
		Color entity = ColorMocksFactory.mockColor();
		OutputColorDTO dto = ColorMocksFactory.mockOutputColorDTO();
		when(repository.findById(VALID_ID)).thenReturn(Optional.of(entity));
		when(mapper.convertEntityToOutputDTO(entity)).thenReturn(dto);
		
		OutputColorDTO result = service.findById(VALID_ID);
		
		CustomizeAsserts.assertOutputColorDTO(result);
		verify(repository, times(1)).findById(VALID_ID);
		verify(mapper, times(1)).convertEntityToOutputDTO(entity);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(repository.findById(INVALID_ID)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> service.findById(INVALID_ID));
		verify(repository, times(1)).findById(INVALID_ID);
		verify(mapper, never()).convertEntityToOutputAllDTO(any());
	}
	
	@Test
	public void findAllShouldReturnDTOPage() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Color> entityPage = ColorMocksFactory.mockColorPage();
		Page<OutputAllColorDTO> dtoPage = ColorMocksFactory.mockOutputAllColorDTOPage();
		when(repository.findAll(pageable)).thenReturn(entityPage);
		when(mapper.convertEntityPageToOutputAllDTOPage(entityPage)).thenReturn(dtoPage);
		
		Page<OutputAllColorDTO> resultPage = service.findAllPaged(pageable);
		CustomizeAsserts.assertPageOutputAllColorDTO(resultPage);
		verify(repository, times(1)).findAll(pageable);
		verify(mapper, times(1)).convertEntityPageToOutputAllDTOPage(entityPage);
	}
	
	@Test
	public void saveShouldReturnDTO() {
		InsertColorDTO insertDTO = ColorMocksFactory.mockInsertColorDTO(1);
		Color insertEntity = new Color();
		Color entity = ColorMocksFactory.mockColor();
		OutputColorDTO outputDTO = ColorMocksFactory.mockOutputColorDTO();
		
		doNothing().when(mapper).copyInputDTOToEntity(insertDTO, insertEntity);
		when(repository.save(insertEntity)).thenReturn(entity);
		when(mapper.convertEntityToOutputDTO(entity)).thenReturn(outputDTO);
		
		OutputColorDTO result = service.save(insertDTO);
		CustomizeAsserts.assertOutputColorDTO(result);
		verify(repository, times(1)).save(insertEntity);
		verify(mapper, times(1)).copyInputDTOToEntity(insertDTO, insertEntity);
		verify(mapper, times(1)).convertEntityToOutputDTO(entity);
	}
	
	@Test
	public void updateShouldReturnDTOWhenIdExists() {
		UpdateColorDTO updateDTO = ColorMocksFactory.mockUpdateColorDTO(1);
		Color updateEntity = ColorMocksFactory.mockColor(1);
		Color entity = ColorMocksFactory.mockColor();
		OutputColorDTO dto = ColorMocksFactory.mockOutputColorDTO();
		
		when(repository.getReferenceById(VALID_ID)).thenReturn(updateEntity);
		doNothing().when(mapper).copyInputDTOToEntity(updateDTO, updateEntity);
		when(repository.save(updateEntity)).thenReturn(entity);
		when(mapper.convertEntityToOutputDTO(entity)).thenReturn(dto);
		
		OutputColorDTO result = service.update(updateDTO, VALID_ID);
		
		CustomizeAsserts.assertOutputColorDTO(result);
		verify(repository, times(1)).getReferenceById(VALID_ID);
		verify(mapper, times(1)).copyInputDTOToEntity(updateDTO, updateEntity);
		verify(repository, times(1)).save(updateEntity);
		verify(mapper, times(1)).convertEntityToOutputDTO(entity);
	}
}
