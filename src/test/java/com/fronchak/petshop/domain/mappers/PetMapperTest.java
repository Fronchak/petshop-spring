package com.fronchak.petshop.domain.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.dtos.pet.InsertPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
import com.fronchak.petshop.domain.dtos.pet.UpdatePetDTO;
import com.fronchak.petshop.domain.entities.Pet;
import com.fronchak.petshop.test.factories.PetMocksFactory;

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
		
		assertEquals(0L, result.getId());
		assertEquals("Mock pet name 0", result.getName());
		assertEquals(20.0, result.getWeightInKg());
		assertEquals(120.0, result.getHeightInCm());
		assertEquals(0L, result.getAnimal().getId());
		assertEquals("Mock animal name 0", result.getAnimal().getName());
		assertEquals(0L, result.getColors().get(0).getId());
		assertEquals("Mock color name 0", result.getColors().get(0).getName());
		assertEquals(1L, result.getColors().get(1).getId());
		assertEquals("Mock color name 1", result.getColors().get(1).getName());
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
		
		assertEquals(0L, entity.getId());
		assertEquals("Mock pet name 0", entity.getName());
		assertEquals(20.0, entity.getWeightInKg());
		assertEquals(120.0, entity.getHeightInCm());
	}
}
