package com.fronchak.petshop.domain.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.dtos.pet.InsertPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputAllPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.dtos.pet.UpdatePetDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.domain.entities.Color;
import com.fronchak.petshop.domain.entities.Pet;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.DatabaseReferenceException;
import com.fronchak.petshop.domain.exceptions.FieldMessage;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.exceptions.ValidationException;
import com.fronchak.petshop.domain.mappers.PetMapper;
import com.fronchak.petshop.domain.repositories.AnimalRepository;
import com.fronchak.petshop.domain.repositories.ColorRepository;
import com.fronchak.petshop.domain.repositories.PetRepository;
import com.fronchak.petshop.test.factories.AnimalMocksFactory;
import com.fronchak.petshop.test.factories.ColorMocksFactory;
import com.fronchak.petshop.test.factories.PetMocksFactory;
import com.fronchak.petshop.util.CustomizeAsserts;

@ExtendWith(SpringExtension.class)
public class PetServiceTest {

	private Long VALID_ID = 0L;
	private Long INVALID_ID = 1L;
	private Long DEPENDENT_ID = 2L;

	@InjectMocks
	private PetService service;
	
	@Mock
	private PetRepository repository;
	
	@Mock
	private AnimalRepository animalRepository;
	
	@Mock
	private ColorRepository colorRepository;
	
	@Mock
	private PetMapper mapper;
	
	@Test
	public void findByIdShouldReturnDTOWhenIdExists() {
		Pet entity = PetMocksFactory.mockPet();
		OutputPetDTO dto = PetMocksFactory.mockOutputPetDTO();
		
		when(repository.findById(VALID_ID)).thenReturn(Optional.of(entity));
		when(mapper.convertEntityToOutputDTO(entity)).thenReturn(dto);
		
		OutputPetDTO result = service.findById(VALID_ID);
		
		CustomizeAsserts.assertOutputPetDTO(result);
		verify(repository, times(1)).findById(VALID_ID);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(repository.findById(INVALID_ID)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> service.findById(INVALID_ID));
		verify(mapper, never()).convertEntityToOutputDTO(any());
	}
	
	@Test
	public void findAllPagedShouldReturnDTOPage() {
		Page<Pet> entityPage = PetMocksFactory.mockPetPage();
		Page<OutputAllPetDTO> dtoPage = PetMocksFactory.mockOutputAllPetDTOPage();
		Pageable pageable = PageRequest.of(0, 10);
		
		when(repository.findAll(pageable)).thenReturn(entityPage);
		when(mapper.convertEntityPageToOutputAllDTOPage(entityPage)).thenReturn(dtoPage);
		
		Page<OutputAllPetDTO> resultPage = service.findAllPaged(pageable);
		
		CustomizeAsserts.assertOutputAllPetDTOPage(resultPage);
		verify(repository, times(1)).findAll(pageable);
	}
	
	@Test
	public void saveShouldReturnDTOAfterSaveNewEntity() {
		InsertPetDTO insertDTO = PetMocksFactory.mockInsertPetDTO();
		Pet outputEntity = PetMocksFactory.mockPet();
		OutputPetDTO outputDTO = PetMocksFactory.mockOutputPetDTO();
		Animal animal = AnimalMocksFactory.mockAnimal();
		Color color1 = ColorMocksFactory.mockColor(0);
		Color color2 = ColorMocksFactory.mockColor(1);
		
		doNothing().when(mapper).copyDTOToEntity(eq(insertDTO), any(Pet.class));
		when(animalRepository.getReferenceById(insertDTO.getIdAnimal())).thenReturn(animal);
		when(colorRepository.getReferenceById(insertDTO.getIdColors().get(0))).thenReturn(color1);
		when(colorRepository.getReferenceById(insertDTO.getIdColors().get(1))).thenReturn(color2);
		when(repository.save(any(Pet.class))).thenReturn(outputEntity);
		when(mapper.convertEntityToOutputDTO(outputEntity)).thenReturn(outputDTO);
		
		ArgumentCaptor<Pet> argumentCaptor = ArgumentCaptor.forClass(Pet.class);
		OutputPetDTO result = service.save(insertDTO);
		verify(repository).save(argumentCaptor.capture());
		
		Pet entity = argumentCaptor.getValue();
		assertNull(entity.getId());
		assertEquals(10L, entity.getAnimal().getId());
		assertEquals(0L, entity.getColors().get(0).getId());
		assertEquals(1L, entity.getColors().get(1).getId());
		
		CustomizeAsserts.assertOutputPetDTO(result);
	}
	
	@Test
	public void saveShouldThrowDatabaseReferenceExceptionWhenAnyOfTheEntityReferencesDoesNotExist() {
		InsertPetDTO insertDTO = PetMocksFactory.mockInsertPetDTO();
		Color color1 = ColorMocksFactory.mockColor(0);
		Color color2 = ColorMocksFactory.mockColor(1);
		
		when(colorRepository.getReferenceById(insertDTO.getIdColors().get(0))).thenReturn(color1);
		when(colorRepository.getReferenceById(insertDTO.getIdColors().get(1))).thenReturn(color2);
		when(repository.save(any(Pet.class))).thenThrow(DataIntegrityViolationException.class);
		assertThrows(DatabaseReferenceException.class, () -> service.save(insertDTO));
	}
	
	@Test
	public void saveShouldThrowValidationExceptionWhenIdColorsHasAnyDuplicateIds() {
		InsertPetDTO insertDTO = PetMocksFactory.mockInsertPetDTO();
		insertDTO.getIdColors().clear();
		insertDTO.getIdColors().add(0L);
		insertDTO.getIdColors().add(0L);
		Animal animal = AnimalMocksFactory.mockAnimal();
		Color color1 = ColorMocksFactory.mockColor(0);
		
		doNothing().when(mapper).copyDTOToEntity(eq(insertDTO), any(Pet.class));
		when(animalRepository.getReferenceById(insertDTO.getIdAnimal())).thenReturn(animal);
		when(colorRepository.getReferenceById(insertDTO.getIdColors().get(0))).thenReturn(color1);
		
		ValidationException exception = assertThrows(ValidationException.class, () -> service.save(insertDTO));
		FieldMessage fieldMessage = exception.getFieldMessage();
		
		assertEquals("Validation error", exception.getMessage());
		assertEquals("colors", fieldMessage.getFieldName());
		assertEquals("Pet's colors cannot be duplicate", fieldMessage.getMessage());
	}
	
	@Test
	public void updateShouldReturnDTOAfterUpdateEntityWhenIdIsExists() {
		UpdatePetDTO updateDTO = PetMocksFactory.mockUpdatePetDTO();
		OutputPetDTO outputDTO = PetMocksFactory.mockOutputPetDTO();
		Pet outputEntity = PetMocksFactory.mockPet();
		Pet entity = PetMocksFactory.mockPet();
		Animal animal = AnimalMocksFactory.mockAnimal();
		Color color1 = ColorMocksFactory.mockColor(0);
		Color color2 = ColorMocksFactory.mockColor(1);
		
		doNothing().when(mapper).copyDTOToEntity(eq(updateDTO), any(Pet.class));
		when(repository.getReferenceById(VALID_ID)).thenReturn(entity);
		when(animalRepository.getReferenceById(updateDTO.getIdAnimal())).thenReturn(animal);
		when(colorRepository.getReferenceById(updateDTO.getIdColors().get(0))).thenReturn(color1);
		when(colorRepository.getReferenceById(updateDTO.getIdColors().get(1))).thenReturn(color2);
		when(repository.save(entity)).thenReturn(outputEntity);
		when(mapper.convertEntityToOutputDTO(outputEntity)).thenReturn(outputDTO);
		
		ArgumentCaptor<Pet> argumentCaptor = ArgumentCaptor.forClass(Pet.class);
		OutputPetDTO result = service.update(updateDTO, VALID_ID);
		verify(repository).save(argumentCaptor.capture());
		Pet entityUpdated = argumentCaptor.getValue();
		
		assertNotNull(entityUpdated.getAnimal());
		assertEquals(entity.getId(), entityUpdated.getId());
		assertEquals(10L, entityUpdated.getAnimal().getId());
		assertEquals(0L, entityUpdated.getColors().get(0).getId());
		assertEquals(1L, entityUpdated.getColors().get(1).getId());
		
		CustomizeAsserts.assertOutputPetDTO(result);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		UpdatePetDTO updateDTO = PetMocksFactory.mockUpdatePetDTO();
		when(repository.getReferenceById(INVALID_ID)).thenThrow(EntityNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class, () -> service.update(eq(updateDTO), INVALID_ID));
		verify(repository, never()).save(any());
	}
	
	@Test
	public void updateShouldThrowDatabaseReferenceExceptionWhenAnyOfTheEntityReferencesExist() {
		UpdatePetDTO updateDTO = PetMocksFactory.mockUpdatePetDTO();
		Color color1 = ColorMocksFactory.mockColor(0);
		Color color2 = ColorMocksFactory.mockColor(1);
		Pet entity = PetMocksFactory.mockPet();
		
		when(repository.getReferenceById(VALID_ID)).thenReturn(entity);
		when(colorRepository.getReferenceById(updateDTO.getIdColors().get(0))).thenReturn(color1);
		when(colorRepository.getReferenceById(updateDTO.getIdColors().get(1))).thenReturn(color2);
		when(repository.save(entity)).thenThrow(DataIntegrityViolationException.class);
		
		assertThrows(DatabaseReferenceException.class, () -> service.update(updateDTO, VALID_ID));
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
	public void deleteShouldThrowDatabaseExceptionWhenIdIdDependent() {
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(DEPENDENT_ID);
		
		assertThrows(DatabaseException.class, () -> service.delete(DEPENDENT_ID));
		verify(repository, times(1)).deleteById(DEPENDENT_ID);
	}
}
