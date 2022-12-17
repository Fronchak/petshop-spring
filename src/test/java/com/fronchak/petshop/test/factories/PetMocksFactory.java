package com.fronchak.petshop.test.factories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fronchak.petshop.domain.dtos.pet.InputPetDTO;
import com.fronchak.petshop.domain.dtos.pet.InsertPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputAllPetDTO;
import com.fronchak.petshop.domain.dtos.pet.OutputPetDTO;
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
		mock.setAnimal(AnimalMocksFactory.mockAnimal(i));
		mock.addColor(ColorMocksFactory.mockColor(0 + 2*i));
		mock.addColor(ColorMocksFactory.mockColor(1 + 2*i));
		return mock;
	}
	
	public static Long mockId(int i) {
		return i + 20L;
	}
	
	public static String mockName(int i) {
		return "Mock pet name " + i;
	}
	
	public static Double mockWeightInKg(int i) {
		return 20.0 + i;
	}
	
	public static Double mockHeightInCm(int i) {
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
		mock.setIdAnimal(AnimalMocksFactory.mockId(0));
		mock.getIdColors().add(ColorMocksFactory.mockId(1));
		mock.getIdColors().add(ColorMocksFactory.mockId(2));
		return mock;
	}
	
	public static UpdatePetDTO mockUpdatePetDTO() {
		return mockUpdatePetDTO(0);
	}
	
	public static UpdatePetDTO mockUpdatePetDTO(int i) {
		UpdatePetDTO mock = new UpdatePetDTO();
		return (UpdatePetDTO) mockInputPetDTO(mock, i);
	}
	
	public static OutputPetDTO mockOutputPetDTO() {
		return mockOutputPetDTO(0);
	}
	
	public static OutputPetDTO mockOutputPetDTO(int i) {
		OutputPetDTO mock = new OutputPetDTO();
		mock.setId(mockId(i));
		mock.setName(mockName(i));
		mock.setHeightInCm(mockHeightInCm(i));
		mock.setWeightInKg(mockWeightInKg(i));
		mock.setAnimal(mockPetAnimalOutputDTO(i));
		mock.addColor(mockPetColorOutputDTO(0 + 2*i));
		mock.addColor(mockPetColorOutputDTO(1 + 2*i));
		return mock;
	}
	
	private static OutputAllPetDTO.PetAnimalOutputDTO mockPetAnimalOutputDTO(int i) {
		OutputAllPetDTO.PetAnimalOutputDTO mock = new OutputAllPetDTO.PetAnimalOutputDTO();
		mock.setId(AnimalMocksFactory.mockId(i));
		mock.setName(AnimalMocksFactory.mockName(i));
		return mock;
	}

	private static OutputAllPetDTO.PetColorOutputDTO mockPetColorOutputDTO(int i) {
		OutputAllPetDTO.PetColorOutputDTO mock = new OutputAllPetDTO.PetColorOutputDTO();
		mock.setId(ColorMocksFactory.mockId(i));
		mock.setName(ColorMocksFactory.mockName(i));
		return mock;
	}
	
	public static Page<Pet> mockPetPage() {
		return new PageImpl<>(mockPageList());
	}
	
	public static List<Pet> mockPageList() {
		List<Pet> list = new ArrayList<>();
		list.add(mockPet(0));
		list.add(mockPet(1));
		return list;
	}
	
	public static Page<OutputAllPetDTO> mockOutputAllPetDTOPage() {
		return new PageImpl<>(mockOutputAllPetDTOList());
	}
	
	public static List<OutputAllPetDTO> mockOutputAllPetDTOList() {
		List<OutputAllPetDTO> list = new ArrayList<>();
		list.add(mockOutputAllPetDTO(0));
		list.add(mockOutputAllPetDTO(1));
		return list;
	}
	
	public static OutputAllPetDTO mockOutputAllPetDTO(int i) {
		OutputAllPetDTO mock = new OutputAllPetDTO();
		mock.setId(mockId(i));
		mock.setName(mockName(i));
		mock.setAnimal(mockPetAnimalOutputDTO(i));
		mock.addColor(mockPetColorOutputDTO(0 + 2 * i));
		mock.addColor(mockPetColorOutputDTO(1 + 2 * i));
		return mock;
	}
}
