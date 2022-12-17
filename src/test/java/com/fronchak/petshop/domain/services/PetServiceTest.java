package com.fronchak.petshop.domain.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.entities.Pet;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.PetMapper;
import com.fronchak.petshop.domain.repositories.PetRepository;
import com.fronchak.petshop.test.factories.PetMocksFactory;
import com.fronchak.petshop.util.CustomizeAsserts;

@ExtendWith(SpringExtension.class)
public class PetServiceTest {

	private Long VALID_ID;
	private Long INVALID_ID;
	private Long DAPENDENT_ID;
	
	@InjectMocks
	private PetService service;
	
	@Mock
	private PetRepository repository;
	
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
}
