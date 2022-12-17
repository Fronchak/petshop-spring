package com.fronchak.petshop.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fronchak.petshop.domain.dtos.animal.OutputAllAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.dtos.color.OutputAllColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;

public class CustomizeAsserts {

	public static void assertOutputColorDTO(OutputColorDTO result) {
		assertEquals(0L,  result.getId());
		assertEquals("Mock color name 0", result.getName());
		assertEquals("Mock color rgb 0", result.getRgb());
		assertEquals("Mock color hex 0", result.getHex());
	}
	
	public static void assertPageOutputAllColorDTO(Page<OutputAllColorDTO> resultPage) {
		List<OutputAllColorDTO> resultList = resultPage.getContent();
		
		OutputAllColorDTO result = resultList.get(0);
		
		assertEquals(0L, result.getId());
		assertEquals("Mock color name 0", result.getName());
		
		result = resultList.get(1);
		
		assertEquals(1L, result.getId());
		assertEquals("Mock color name 1", result.getName());
		
		result = resultList.get(2);
		
		assertEquals(2L, result.getId());
		assertEquals("Mock color name 2", result.getName());
	}
	
	public static void assertAnimalOutputDTO(OutputAnimalDTO result) {
		assertEquals(0L, result.getId());
		assertEquals("Mock animal name 0", result.getName());
		assertEquals("Mock animal description 0", result.getDescription());
	}
	
	public static void assertOutputAllAnimalDTOPage(Page<OutputAllAnimalDTO> resultPage) {
		List<OutputAllAnimalDTO> resultList = resultPage.getContent();
		
		OutputAllAnimalDTO result = resultList.get(0);
		assertEquals(0L, result.getId());
		assertEquals("Mock animal name 0", result.getName());
		
		result = resultList.get(1);
		assertEquals(1L, result.getId());
		assertEquals("Mock animal name 1", result.getName());
		
		result = resultList.get(2);
		assertEquals(2L, result.getId());
		assertEquals("Mock animal name 2", result.getName());
	}
}
