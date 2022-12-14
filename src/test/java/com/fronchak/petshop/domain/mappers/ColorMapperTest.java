package com.fronchak.petshop.domain.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.dtos.color.InsertColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputAllColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.dtos.color.UpdateColorDTO;
import com.fronchak.petshop.domain.entities.Color;
import com.fronchak.petshop.test.factories.ColorMocksFactory;
import com.fronchak.petshop.util.CustomizeAsserts;

@ExtendWith(SpringExtension.class)
public class ColorMapperTest {

	private ColorMapper mapper;
	
	@BeforeEach
	void setUp() {
		mapper = new ColorMapper();
	}
	
	@Test
	public void copyInsertDTOToEntityShouldCopyValuesCorrectly() {
		InsertColorDTO dto = ColorMocksFactory.mockInsertColorDTO();
		Color entity = new Color();
		
		mapper.copyInputDTOToEntity(dto, entity);
		
		assertNull(entity.getId());
		assertEquals("Mock name 0", entity.getName());
		assertEquals("Mock rgb 0", entity.getRgb());
		assertEquals("Mock hex 0", entity.getHex());
	}
	
	@Test
	public void copyUpdateDTOToEntityShouldCopyValuesCorrectly() {
		UpdateColorDTO dto = ColorMocksFactory.mockUpdateColorDTO();
		Color entity = new Color();
		entity.setId(1L);
		
		mapper.copyInputDTOToEntity(dto, entity);
		
		assertEquals(1L, entity.getId());
		assertEquals("Mock name 0", entity.getName());
		assertEquals("Mock rgb 0", entity.getRgb());
		assertEquals("Mock hex 0", entity.getHex());
	}
	
	@Test
	public void convertEntityToOutputDTOShouldConvertCorrectly() {
		Color entity = ColorMocksFactory.mockColor();
		OutputColorDTO result = mapper.convertEntityToOutputDTO(entity);
		CustomizeAsserts.assertOutputColorDTO(result);
	}
	
	@Test
	public void convertEntityPageToOutputAllDTOPageShouldConvertCorrctly() {
		Page<Color> page = ColorMocksFactory.mockColorPage();
		
		Page<OutputAllColorDTO> resultPage = mapper.convertEntityPageToOutputAllDTOPage(page);
		CustomizeAsserts.assertPageOutputAllColorDTO(resultPage);
	}
	
}
