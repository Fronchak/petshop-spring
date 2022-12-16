package com.fronchak.petshop.domain.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.dtos.animal.InsertAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAllAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.UpdateAnimalDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.AnimalMapper;
import com.fronchak.petshop.domain.repositories.AnimalRepository;
import com.fronchak.petshop.test.factories.AnimalMocksFactory;
import com.fronchak.petshop.util.CustomizeAsserts;

@ExtendWith(SpringExtension.class)
public class AnimalServiceTest {

	private Long VALID_ID = 1L;
	private Long INVALID_ID = 2L;
	private Long DEPENDENT_ID = 3L;
	
	@Mock
	private AnimalRepository repository;
	
	@Mock
	private AnimalMapper mapper;
	
	@InjectMocks
	private AnimalService service;
	
	@Test
	public void findByIdShouldReturnDTOWhenIdExists() {
		Animal entity = AnimalMocksFactory.mockAnimal();
		OutputAnimalDTO dto = AnimalMocksFactory.mockOutputAnimalDTO();
		
		when(repository.findById(VALID_ID)).thenReturn(Optional.of(entity));
		when(mapper.convertEntityOutputDTO(entity)).thenReturn(dto);
		
		OutputAnimalDTO result = service.findById(VALID_ID);
		CustomizeAsserts.assertAnimalOutputDTO(result);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(repository.findById(INVALID_ID)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> service.findById(INVALID_ID));
	}
	
	@Test
	public void findAllPagedShouldReturnDTOPage() {
		Pageable pageable = PageRequest.of(0, 5);
		Page<Animal> entityPage = AnimalMocksFactory.mockAnimalPage();
		Page<OutputAllAnimalDTO> dtoPage = AnimalMocksFactory.mockOutputAllAnimalDTOPage();
		
		when(repository.findAll(pageable)).thenReturn(entityPage);
		when(mapper.convertEntityPageToOutputAllDTOPage(entityPage)).thenReturn(dtoPage);
		
		Page<OutputAllAnimalDTO> resultPage = service.findAllPaged(pageable);
		CustomizeAsserts.assertOutputAllAnimalDTOPage(resultPage);
	}
	
	@Test
	public void saveShouldReturnDTOAfterSaveNewEntity() {
		InsertAnimalDTO insertDTO = AnimalMocksFactory.mockInsertAnimalDTO();
		Animal entity = AnimalMocksFactory.mockAnimal(1);
		OutputAnimalDTO dto = AnimalMocksFactory.mockOutputAnimalDTO();
		
		doNothing().when(mapper).copyInputDTOToEntity(eq(insertDTO), any());
		when(repository.save(any(Animal.class))).thenReturn(entity);
		when(mapper.convertEntityOutputDTO(entity)).thenReturn(dto);
		
		OutputAnimalDTO result = service.save(insertDTO);
		
		CustomizeAsserts.assertAnimalOutputDTO(result);
		verify(repository, times(1)).save(any(Animal.class));
	}
	
	@Test
	public void updateShouldReturnDTOWhenIdExists() {
		UpdateAnimalDTO updateDTO = AnimalMocksFactory.mockUpdateAnimalDTO();
		Animal updateEntity = AnimalMocksFactory.mockAnimal(1);
		Animal entity = AnimalMocksFactory.mockAnimal();
		OutputAnimalDTO dto = AnimalMocksFactory.mockOutputAnimalDTO();
		
		when(repository.getReferenceById(VALID_ID)).thenReturn(updateEntity);
		doNothing().when(mapper).copyInputDTOToEntity(updateDTO, updateEntity);
		when(repository.save(updateEntity)).thenReturn(entity);
		when(mapper.convertEntityOutputDTO(entity)).thenReturn(dto);
		
		OutputAnimalDTO result = service.update(updateDTO, VALID_ID);
		
		CustomizeAsserts.assertAnimalOutputDTO(result);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundAndIdDoesNotExist() {
		UpdateAnimalDTO updateDTO = AnimalMocksFactory.mockUpdateAnimalDTO();
		
		when(repository.getReferenceById(INVALID_ID)).thenThrow(EntityNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class, () -> service.update(updateDTO, INVALID_ID));
		verify(repository, never()).save(any());
	}
	
	@Test
	public void deleteShouldNotThrowExceptionWhenIdExists() {
		doNothing().when(repository).deleteById(VALID_ID);
		
		assertDoesNotThrow(() -> service.delete(VALID_ID));
		verify(repository, times(1)).deleteById(VALID_ID);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(INVALID_ID);
		
		assertThrows(ResourceNotFoundException.class, () -> service.delete(INVALID_ID));
		verify(repository, times(1)).deleteById(INVALID_ID);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIdIsDependent() {
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(DEPENDENT_ID);
		
		assertThrows(DatabaseException.class, () -> service.delete(DEPENDENT_ID));
		verify(repository, times(1)).deleteById(DEPENDENT_ID);
	}
}
