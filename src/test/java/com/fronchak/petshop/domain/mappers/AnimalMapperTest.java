package com.fronchak.petshop.domain.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.dtos.animal.InsertAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAllAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.UpdateAnimalDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.test.factories.AnimalMocksFactory;
import com.fronchak.petshop.util.CustomizeAsserts;

@ExtendWith(SpringExtension.class)
public class AnimalMapperTest {

	private AnimalMapper mapper;
	
	@BeforeEach
	void setUp() {
		mapper = new AnimalMapper();
	}
	
	@Test
	public void convertEntityToOutputDTOShouldConvertCorrectly() {
		Animal entity = AnimalMocksFactory.mockAnimal();
		
		OutputAnimalDTO result = mapper.convertEntityOutputDTO(entity);
		CustomizeAsserts.assertAnimalOutputDTO(result);
	}
	
	@Test
	public void copyInputDTOToEntityUsingInsertDTOShouldCopyValuesCorrectly() {
		InsertAnimalDTO dto = AnimalMocksFactory.mockInsertAnimalDTO();
		Animal entity = new Animal();
		
		mapper.copyInputDTOToEntity(dto, entity);
		
		assertNull(entity.getId());
		assertEquals("Mock name 0", entity.getName());
		assertEquals("Mock description 0", entity.getDescription());
	}
	
	@Test
	public void copyInputDTOToEntityUsingUpdateDTOShouldCopyValuesCorrectly() {
		UpdateAnimalDTO dto = AnimalMocksFactory.mockUpdateAnimalDTO();
		Animal entity = AnimalMocksFactory.mockAnimal(1);
		
		mapper.copyInputDTOToEntity(dto, entity);
		
		assertEquals(1L, entity.getId());
		assertEquals("Mock name 0", entity.getName());
		assertEquals("Mock description 0", entity.getDescription());
	}
	
	@Test
	public void convertEntityPageToOutputAllDTOPageShouldConvertPageCorrectly() {
		Page<Animal> page = AnimalMocksFactory.mockAnimalPage();
		
		Page<OutputAllAnimalDTO> resultPage = mapper.convertEntityPageToOutputAllDTOPage(page);
		CustomizeAsserts.assertOutputAllAnimalDTOPage(resultPage);
	}
}
