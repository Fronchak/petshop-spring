package com.fronchak.petshop.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fronchak.petshop.domain.entities.Client;

@DataJpaTest
public class ClientRepositoryTest {

	@Autowired
	private ClientRepository repository;
	
	@Test
	public void findByEmailShoudReturnNullWhenEmailDoesNotExist() {	
		Client result = repository.findByEmail("invalidEmail@gmail.com");
		assertNull(result);
	}
	
	@Test
	public void findByEmailShouldReturnClientWhenEmailExists() {
		Client result = repository.findByEmail("gabriel@gmail.com");
		assertNotNull(result);
		assertEquals(1L, result.getId());
	}
	
	@Test
	public void findByCpfShouldReturnNullWhenCpfDoesNotExist() {
		Client result = repository.findByCpf("11111111111");
		assertNull(result);
	}
	
	@Test
	public void findByIdShouldReturnClientWhenCpfExists() {
		Client result = repository.findByCpf("13345678910");
		assertNotNull(result);
		assertEquals(2L, result.getId());
	}
}
