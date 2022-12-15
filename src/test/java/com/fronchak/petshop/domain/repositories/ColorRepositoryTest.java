package com.fronchak.petshop.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fronchak.petshop.domain.entities.Color;

@DataJpaTest
public class ColorRepositoryTest {

	private Long VALID_ID = 1L;
	private Long INVALID_ID = 1000L;
	
	@Autowired
	private ColorRepository repository;
	
	@Test
	public void findByIdShouldReturnEntityWhenIdExists() {
		
		Optional<Color> obj = repository.findById(VALID_ID);
		
		assertTrue(obj.isPresent());
		
		Color result = obj.get();
		
		assertEquals(1L, result.getId());
		assertEquals("Black", result.getName());
		assertEquals("#000000", result.getHex());
		assertEquals("(0,0,0)", result.getRgb());
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
		Optional<Color> result = repository.findById(INVALID_ID);
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void findByNameShouldReturnEntityWhenNameExists() {
		Color result = repository.findByName("Red");
		assertNotNull(result);
		assertEquals("(255,0,0)", result.getRgb());
		assertEquals("#FF0000", result.getHex());
	}
	
	@Test
	public void findByNameShouldReturnNullWhenNameDoesNotExist() {
		Color result = repository.findByName("AAA");
		assertNull(result);
	}
	
	@Test
	public void findByRgbShouldReturnEntityWhenRgbExists() {
		Color result = repository.findByRgb("(0,0,0)");
		assertNotNull(result);
		assertEquals("Black", result.getName());
		assertEquals("#000000", result.getHex());
	}
	
	@Test
	public void findByRgbShouldReturnNullWhenRgbDoesNotExist() {
		Color result = repository.findByRgb("()");
		assertNull(result);
	}
	
	@Test
	public void findByHexShouldReturnEntityWhenHexExists() {
		Color result = repository.findByHex("#FFFFFF");
		assertNotNull(result);
		assertEquals("White", result.getName());
		assertEquals("(255,255,255)", result.getRgb());
	}
	
	@Test
	public void findByHexShouldReturnNullWhenHexDoesNotExist() {
		Color result = repository.findByHex("###");
		assertNull(result);
	}
}
