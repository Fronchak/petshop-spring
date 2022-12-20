package com.fronchak.petshop.domain.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.dtos.client.InsertClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputAllClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.dtos.client.UpdateClientDTO;
import com.fronchak.petshop.domain.entities.Client;
import com.fronchak.petshop.test.factories.ClientMocksFactory;
import com.fronchak.petshop.util.CustomizeAsserts;

@ExtendWith(SpringExtension.class)
public class ClientMapperTest {

	private ClientMapper mapper;
	
	@BeforeEach
	public void setUp() {
		mapper = new ClientMapper();
	}
	
	@Test
	public void convertEntityToOutputDTOShouldConvertCorrectly() {
		Client entity = ClientMocksFactory.mockClient();
		OutputClientDTO result = mapper.convertEntityToOutputDTO(entity);
		CustomizeAsserts.assertOutputClientDTO(result);
	}
	
	@Test
	public void convertEntityPageToOutputAllDTOShouldConvertCorrectly() {
		Page<Client> entityPage = ClientMocksFactory.mockClientPage();
		
		Page<OutputAllClientDTO> resultPage = mapper.convertEntityPageToOutputAllClientDTO(entityPage);
		CustomizeAsserts.assertOutputAllClientDTOPage(resultPage);
	}
	
	@Test
	public void copyInputDTOToEntityShouldCopyValuesCorrectlyWhenUsingInsertDTO() {
		InsertClientDTO dto = ClientMocksFactory.mockInsertClientDTO();
		Client entity = new Client();
		
		mapper.copyInputDTOToEntity(dto, entity);
		
		assertNull(entity.getId());
		assertEquals("Mock client firstName 0", entity.getFirstName());
		assertEquals("Mock client lastName 0", entity.getLastName());
		assertEquals("MockClientEmail0@gmail.com", entity.getEmail());
		assertEquals("1234567890", entity.getCpf());
		assertEquals(LocalDate.of(2022, 12, 1), entity.getBirthDate());
	}
	
	@Test
	public void copyInputDTOToEntityShouldCopyValuesCorrectlyWhenUsingUpdateDTO() {
		UpdateClientDTO dto = ClientMocksFactory.mockUpdateClientDTO();
		Client entity = new Client();
		entity.setId(1L);
		
		mapper.copyInputDTOToEntity(dto, entity);
		
		assertEquals(1L, entity.getId());
		assertEquals("Mock client firstName 0", entity.getFirstName());
		assertEquals("Mock client lastName 0", entity.getLastName());
		assertEquals("MockClientEmail0@gmail.com", entity.getEmail());
		assertEquals("1234567890", entity.getCpf());
		assertEquals(LocalDate.of(2022, 12, 1), entity.getBirthDate());
	}
}
