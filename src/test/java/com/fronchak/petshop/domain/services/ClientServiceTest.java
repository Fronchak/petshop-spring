package com.fronchak.petshop.domain.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.fronchak.petshop.domain.dtos.client.InsertClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputAllClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.dtos.client.UpdateClientDTO;
import com.fronchak.petshop.domain.entities.Client;
import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.mappers.ClientMapper;
import com.fronchak.petshop.domain.repositories.ClientRepository;
import com.fronchak.petshop.test.factories.ClientMocksFactory;
import com.fronchak.petshop.util.CustomizeAsserts;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

	private Long VALID_ID = 1L;
	private Long INVALID_ID = 2L;
	private Long DEPENDENT_ID = 3L;
	
	@InjectMocks
	private ClientService service;
	
	@Mock
	private ClientRepository repository;
	
	@Mock
	private ClientMapper mapper;
	
	@Test
	public void findByIdShouldReturnDTOWhenIdExists() {
		Client entity = ClientMocksFactory.mockClient();
		OutputClientDTO dto = ClientMocksFactory.mockOutputClientDTO();
		when(repository.findById(VALID_ID)).thenReturn(Optional.of(entity));
		when(mapper.convertEntityToOutputDTO(entity)).thenReturn(dto);
		
		OutputClientDTO result = service.findById(VALID_ID);
		CustomizeAsserts.assertOutputClientDTO(result);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundWhenIdDoesNotExist() {
		when(repository.findById(INVALID_ID)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> service.findById(INVALID_ID));
	}
	
	@Test
	public void findAllPagedShouldReturnOutputAllDTOPage() {
		Page<Client> entityPage = ClientMocksFactory.mockClientPage();
		Page<OutputAllClientDTO> dtoPage = ClientMocksFactory.mockOutputAllClientDTOPage();
		Pageable pageable = PageRequest.of(0, 10);
		
		when(repository.findAll(pageable)).thenReturn(entityPage);
		when(mapper.convertEntityPageToOutputAllClientDTO(entityPage)).thenReturn(dtoPage);
		
		Page<OutputAllClientDTO> resultPage = service.findAllPaged(pageable);
		CustomizeAsserts.assertOutputAllClientDTOPage(resultPage);
	}
	
	@Test
	public void deleteShouldNotThrowAnyExceptionWhenIdExists() {
		doNothing().when(repository).deleteById(VALID_ID);
		
		assertDoesNotThrow(() -> service.delete(VALID_ID));
		verify(repository, times(1)).deleteById(VALID_ID);
	}
	
	@Test
	public void deleteShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExist() {
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
	
	@Test
	public void saveShouldReturnOutputDTOAfterSaveNewEntity() {
		InsertClientDTO insertDTO = ClientMocksFactory.mockInsertClientDTO();
		Client entity = ClientMocksFactory.mockClient();
		OutputClientDTO outputClientDTO = ClientMocksFactory.mockOutputClientDTO();
		
		doNothing().when(mapper).copyInputDTOToEntity(eq(insertDTO), any(Client.class));
		when(repository.save(any(Client.class))).thenReturn(entity);
		when(mapper.convertEntityToOutputDTO(entity)).thenReturn(outputClientDTO);
		
		ArgumentCaptor<Client> argumentCaptor = ArgumentCaptor.forClass(Client.class);
		OutputClientDTO result = service.save(insertDTO);
		CustomizeAsserts.assertOutputClientDTO(result);
		verify(mapper, times(1)).copyInputDTOToEntity(eq(insertDTO), any(Client.class));
		verify(repository, times(1)).save(any(Client.class));
		
		verify(repository).save(argumentCaptor.capture());
		Client entitySaved = argumentCaptor.getValue();
		assertNull(entitySaved.getId());
		assertNull(entitySaved.getFirstName());
		assertNull(entitySaved.getLastName());
		assertNull(entitySaved.getEmail());
		assertNull(entitySaved.getCpf());
		assertNull(entitySaved.getBirthDate());
		assertTrue(entitySaved.getPets().isEmpty());
	}
	
	@Test
	public void updateShouldReturnOutputDTOAfterUpdateEntityWhenIdExists() {
		UpdateClientDTO updateDTO = ClientMocksFactory.mockUpdateClientDTO();
		OutputClientDTO outputDTO = ClientMocksFactory.mockOutputClientDTO();
		Client inputEntity = ClientMocksFactory.mockClient(1);
		Client outputEntity = ClientMocksFactory.mockClient();
		
		when(repository.getReferenceById(VALID_ID)).thenReturn(inputEntity);
		doNothing().when(mapper).copyInputDTOToEntity(updateDTO, inputEntity);
		when(repository.save(inputEntity)).thenReturn(outputEntity);
		when(mapper.convertEntityToOutputDTO(outputEntity)).thenReturn(outputDTO);
		
		ArgumentCaptor<Client> argumentCaptor = ArgumentCaptor.forClass(Client.class);
		OutputClientDTO result = service.update(updateDTO, VALID_ID);
		CustomizeAsserts.assertOutputClientDTO(result);
		
		verify(repository).save(argumentCaptor.capture());
		Client entitySaved = argumentCaptor.getValue();
		assertEquals(31L, entitySaved.getId());
		assertEquals("Mock client firstName 1",entitySaved.getFirstName());
		assertEquals("Mock client lastName 1", entitySaved.getLastName());
		assertEquals("MockClientEmail1@gmail.com", entitySaved.getEmail());
		assertEquals("1234567891", entitySaved.getCpf());
		assertEquals(LocalDate.of(2022, 12, 2), entitySaved.getBirthDate());
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		UpdateClientDTO updateDTO = ClientMocksFactory.mockUpdateClientDTO();	
		when(repository.getReferenceById(INVALID_ID)).thenThrow(EntityNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class, () -> service.update(updateDTO, INVALID_ID));
		verify(repository, never()).save(any(Client.class));
	}
}
