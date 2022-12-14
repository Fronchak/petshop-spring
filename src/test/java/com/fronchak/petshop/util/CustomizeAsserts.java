package com.fronchak.petshop.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fronchak.petshop.domain.dtos.color.OutputAllColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;

public class CustomizeAsserts {

	public static void assertOutputColorDTO(OutputColorDTO result) {
		assertEquals(0L,  result.getId());
		assertEquals("Mock name 0", result.getName());
		assertEquals("Mock rgb 0", result.getRgb());
		assertEquals("Mock hex 0", result.getHex());
	}
	
	public static void assertPageOutputAllColorDTO(Page<OutputAllColorDTO> resultPage) {
		List<OutputAllColorDTO> resultList = resultPage.getContent();
		
		OutputAllColorDTO result = resultList.get(0);
		
		assertEquals(0L, result.getId());
		assertEquals("Mock name 0", result.getName());
		
		result = resultList.get(1);
		
		assertEquals(1L, result.getId());
		assertEquals("Mock name 1", result.getName());
		
		result = resultList.get(2);
		
		assertEquals(2L, result.getId());
		assertEquals("Mock name 2", result.getName());
	}
}
