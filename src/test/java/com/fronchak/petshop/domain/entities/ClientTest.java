package com.fronchak.petshop.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ClientTest {

	private Client client;
	
	@BeforeEach
	public void setUp() {
		client = new Client();
	}
	
	@Test
	public void addPetShouldAddPetWhenNoPetsAreRepeated() {
		Pet pet1 = new Pet(1L);
		Pet pet2 = new Pet(2L); 
		
		client.addPet(pet1);
		client.addPet(pet2);
		
		assertTrue(client.getPets().contains(pet1));
		assertTrue(client.getPets().contains(pet2));
		assertEquals(2, client.getPets().size());
	}
	
	@Test
	public void addPetShouldIgnoreRepeatedPet() {
		Pet pet1 = new Pet(1L);
		Pet pet2 = new Pet(1L);
		
		client.addPet(pet1);
		client.addPet(pet2);
		
		assertTrue(client.getPets().contains(pet1));
		assertEquals(1, client.getPets().size());
	}
}
