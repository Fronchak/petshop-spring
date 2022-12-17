package com.fronchak.petshop.test.factories;

import com.fronchak.petshop.domain.dtos.pet.InputPetDTO;
import com.fronchak.petshop.domain.dtos.pet.InsertPetDTO;
import com.fronchak.petshop.domain.dtos.pet.UpdatePetDTO;
import com.fronchak.petshop.domain.entities.Pet;

public class PetMocksFactory {

	public static Pet mockPet() {
		return mockPet(0);
	}
	
	public static Pet mockPet(int i) {
		Pet mock = new Pet();
		mock.setId(mockId(i));
		mock.setName(mockName(i));
		mock.setHeightInCm(mockHeightInCm(i));
		mock.setWeightInKg(mockWeightInKg(i));
		mock.setAnimal(AnimalMocksFactory.mockAnimal());
		mock.addColor(ColorMocksFactory.mockColor(0));
		mock.addColor(ColorMocksFactory.mockColor(1));
		return mock;
	}
	
	private static Long mockId(int i) {
		return i + 0L;
	}
	
	private static String mockName(int i) {
		return "Mock pet name " + i;
	}
	
	private static Double mockWeightInKg(int i) {
		return 20.0 + i;
	}
	
	private static Double mockHeightInCm(int i) {
		return 120.0 + i;
	}
	
	public static InsertPetDTO  mockInsertPetDTO() {
		return mockInsertPetDTO(0);
	}
	
	public static InsertPetDTO mockInsertPetDTO(int i) {
		InsertPetDTO mock = new InsertPetDTO();
		return (InsertPetDTO) mockInputPetDTO(mock, i);
	}
	
	private static InputPetDTO mockInputPetDTO(InputPetDTO mock, int i) {
		mock.setName(mockName(i));
		mock.setHeightInCm(mockHeightInCm(i));
		mock.setWeightInKg(mockWeightInKg(i));
		mock.setIdAnimal(0L);
		mock.getIdColors().add(0L);
		mock.getIdColors().add(1L);
		return mock;
	}
	
	public static UpdatePetDTO mockUpdatePetDTO() {
		return mockUpdatePetDTO(0);
	}
	
	public static UpdatePetDTO mockUpdatePetDTO(int i) {
		UpdatePetDTO mock = new UpdatePetDTO();
		return (UpdatePetDTO) mockInputPetDTO(mock, i);
	}
}
