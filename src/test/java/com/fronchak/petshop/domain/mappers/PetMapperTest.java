package com.fronchak.petshop.domain.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.dtos.pet.InsertPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputAllPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.dtos.pet.UpdatePetDTO;
import com.fronchak.petshop.domain.entities.Pet;
import com.fronchak.petshop.test.factories.PetMocksFactory;
import com.fronchak.petshop.util.CustomizeAsserts;

@ExtendWith(SpringExtension.class)
public class PetMapperTest {

	private PetMapper mapper;
	
	@BeforeEach
	void setUp() {
		mapper = new PetMapper();
	}
	
	@Test
	public void convertEntityToOutputDTOShouldConvertCorrectly() {
		Pet entity = PetMocksFactory.mockPet();
		
		OutputPetDTO result = mapper.convertEntityToOutputDTO(entity);
		
		CustomizeAsserts.assertOutputPetDTO(result);
	}
	
	@Test
	public void copyDTOToEntityShouldCopyValuesCorrectlyWhenUsingInsertDTO() {
		InsertPetDTO dto = PetMocksFactory.mockInsertPetDTO();
		Pet entity = new Pet();
		
		mapper.copyDTOToEntity(dto, entity);
		
		assertNull(entity.getId());
		assertEquals("Mock pet name 0", entity.getName());
		assertEquals(20.0, entity.getWeightInKg());
		assertEquals(120.0, entity.getHeightInCm());
	}
	
	@Test
	public void copyDTOToEntityShouldCopyValuesCorrectlyWhenUsingUpdateDTO() {
		UpdatePetDTO dto = PetMocksFactory.mockUpdatePetDTO();
		Pet entity = PetMocksFactory.mockPet();
		
		mapper.copyDTOToEntity(dto, entity);
		
		assertEquals(20L, entity.getId());
		assertEquals("Mock pet name 0", entity.getName());
		assertEquals(20.0, entity.getWeightInKg());
		assertEquals(120.0, entity.getHeightInCm());
	}
	
	@Test
	public void convertEntityPageToOutputAllDTOPageShouldConvertCorrectly() {
		Page<Pet> entityPage = PetMocksFactory.mockPetPage();
		
		Page<OutputAllPetDTO> resultPage = mapper.convertEntityPageToOutputAllDTOPage(entityPage);
		
		CustomizeAsserts.assertOutputAllPetDTOPage(resultPage);
	}
}
