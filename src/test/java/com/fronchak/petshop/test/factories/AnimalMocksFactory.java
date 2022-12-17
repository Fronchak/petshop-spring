package com.fronchak.petshop.test.factories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fronchak.petshop.domain.dtos.animal.InputAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.InsertAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAllAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.UpdateAnimalDTO;
import com.fronchak.petshop.domain.entities.Animal;

public class AnimalMocksFactory {

	public static Animal mockAnimal() {
		return mockAnimal(0);
	}
	
	public static Animal mockAnimal(int i) {
		Animal mock = new Animal();
		mock.setId(mockId(i));
		mock.setName(mockName(i));
		mock.setDescription(mockDescription(i));
		return mock;
	}
	
	public static Long mockId(int i) {
		return i + 10L;
	}
	
	public static String mockName(int i) {
		return "Mock animal name " + i;
	}
	
	public static String mockDescription(int i) {
		return "Mock animal description " + i;
	}
	
	public static InsertAnimalDTO mockInsertAnimalDTO() {
		return mockInsertAnimalDTO(0);
	}
	
	public static InsertAnimalDTO mockInsertAnimalDTO(int i) {
		InsertAnimalDTO mock = new InsertAnimalDTO();
		return (InsertAnimalDTO) mockInputAnimalDTO(mock, i);
	}
	
	private static InputAnimalDTO mockInputAnimalDTO(InputAnimalDTO mock, int  i) {
		mock.setName(mockName(i));
		mock.setDescription(mockDescription(i));
		return mock;
	}
	
	public static UpdateAnimalDTO mockUpdateAnimalDTO() {
		return mockUpdateAnimalDTO(0);
	}
	
	public static UpdateAnimalDTO mockUpdateAnimalDTO(int i) {
		UpdateAnimalDTO mock = new UpdateAnimalDTO();
		return (UpdateAnimalDTO) mockInputAnimalDTO(mock, i);
	}
	
	public static Page<Animal> mockAnimalPage() {
		return new PageImpl<>(mockAnimalList());
	}
	
	public static List<Animal> mockAnimalList() {
		List<Animal> list = new ArrayList<>();
		list.add(mockAnimal(0));
		list.add(mockAnimal(1));
		list.add(mockAnimal(2));
		return list;
	}
	
	public static Page<OutputAllAnimalDTO> mockOutputAllAnimalDTOPage() {
		return new PageImpl<>(mockOutputAllAnimalDTOList());
	}
	
	public static List<OutputAllAnimalDTO> mockOutputAllAnimalDTOList() {
		List<OutputAllAnimalDTO> list = new ArrayList<>();
		list.add(mockOutputAllAnimalDTO(0));
		list.add(mockOutputAllAnimalDTO(1));
		list.add(mockOutputAllAnimalDTO(2));
		return list;
	}
	
	public static OutputAllAnimalDTO mockOutputAllAnimalDTO(int i) {
		OutputAllAnimalDTO mock = new OutputAllAnimalDTO();
		mock.setId(mockId(i));
		mock.setName(mockName(i));
		return mock;
	}
	
	public static OutputAnimalDTO mockOutputAnimalDTO() {
		return mockOutputAnimalDTO(0);
	}
	
	public static OutputAnimalDTO mockOutputAnimalDTO(int i) {
		OutputAnimalDTO mock = new OutputAnimalDTO();
		mock.setId(mockId(i));
		mock.setName(mockName(i));
		mock.setDescription(mockDescription(i));
		return mock;
	}
}
