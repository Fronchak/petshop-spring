package com.fronchak.petshop.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fronchak.petshop.domain.entities.Animal;

@DataJpaTest
public class AnimalRepositoryTest {

	@Autowired
	private AnimalRepository repository;
	
	@Test
	public void findByNameShouldReturnEntityWhenNameExists() {
		Animal entity = repository.findByName("Dog");
		
		assertNotNull(entity);
		assertEquals(1L, entity.getId());
		assertEquals("Dog", entity.getName());
	}
	
	@Test
	public void findByNameShouldReturnNullWhenNameDoesNotExist() {
		Animal entity = repository.findByName("Some name");
		
		assertNull(entity);
	}
	
}
