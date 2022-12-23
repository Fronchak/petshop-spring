package com.fronchak.petshop.test.factories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fronchak.petshop.domain.dtos.client.InputClientDTO;
import com.fronchak.petshop.domain.dtos.client.InsertClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputAllClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.dtos.client.UpdateClientDTO;
import com.fronchak.petshop.domain.entities.Client;

public class ClientMocksFactory {

	public static Client mockClient() {
		return mockClient(0);
	}
	
	public static Client mockClient(int i) {
		Client mock = new Client();
		mock.setId(mockId(i));
		mock.setFirstName(mockFirstName(i));
		mock.setLastName(mockLastName(i));
		mock.setEmail(mockEmail(i));
		mock.setCpf(mockCpf(i));
		mock.setBirthDate(mockBirthDate(i));
		mock.addPet(PetMocksFactory.mockPet(0));
		mock.addPet(PetMocksFactory.mockPet(1));
		return mock;
	}
	
	public static Long mockId(int i) {
		return 30L + i;
	}
	
	public static String mockFirstName(int i) {
		return "Mock client firstName " + i;
	}
	
	public static String mockLastName(int i) {
		return "Mock client lastName " + i;
	}
	
	public static String mockEmail(int i) {
		return "MockClientEmail" + i + "@gmail.com";
	}
	
	public static String mockCpf(int i) {
		return "1234567890" + i;
	}
	
	public static LocalDate mockBirthDate(int i) {
		return LocalDate.of(2022, 12, i + 1);
	}
	
	public static Page<Client> mockClientPage() {
		return new PageImpl<>(mockClientList());
	}
	
	public static List<Client> mockClientList() {
		List<Client> list = new ArrayList<>();
		list.add(mockClient(0));
		list.add(mockClient(1));
		list.add(mockClient(2));
		return list;
	}
	
	public static InsertClientDTO mockInsertClientDTO() {
		return mockInsertClientDTO(0);
	}
	
	public static InsertClientDTO mockInsertClientDTO(int i) {
		InsertClientDTO mock = new InsertClientDTO();
		return (InsertClientDTO) mockInputClientDTO(mock, i);
	}
	
	private static InputClientDTO mockInputClientDTO(InputClientDTO mock, int i) {
		mock.setFirstName(mockFirstName(i));
		mock.setLastName(mockLastName(i));
		mock.setEmail(mockEmail(i));
		mock.setCpf(mockCpf(i));
		mock.setBirthDate(mockBirthDate(i));
		return mock;
	}
	
	public static UpdateClientDTO mockUpdateClientDTO() {
		return mockUpdateClientDTO(0);
	}
	
	public static UpdateClientDTO mockUpdateClientDTO(int i) {
		UpdateClientDTO mock = new UpdateClientDTO();
		return (UpdateClientDTO) mockInputClientDTO(mock, i);
	}
	
	public static OutputClientDTO mockOutputClientDTO() {
		return mockOutputClientDTO(0);
	}
	
	public static OutputClientDTO mockOutputClientDTO(int i) {
		OutputClientDTO dto = new OutputClientDTO();
		dto.setId(mockId(i));
		dto.setName(mockName(i));
		dto.setEmail(mockEmail(i));
		dto.addPet(mockOutputClientPetDTO(0));
		dto.addPet(mockOutputClientPetDTO(1));
		return dto;
	}
	
	public static String mockName(int i) {
		return mockFirstName(i) + " " + mockLastName(i);
	}
	
	public static OutputClientDTO.OutputClientPetDTO mockOutputClientPetDTO(int i) {
		OutputClientDTO.OutputClientPetDTO mock = new OutputClientDTO.OutputClientPetDTO();
		mock.setId(PetMocksFactory.mockId(i));
		mock.setPetName(PetMocksFactory.mockName(i));
		mock.setAnimalName(AnimalMocksFactory.mockName(i));
		return mock;
	}
	
	public static Page<OutputAllClientDTO> mockOutputAllClientDTOPage() {
		return new PageImpl<>(mockOutputAllClientDTOList());
	}
	
	public static List<OutputAllClientDTO> mockOutputAllClientDTOList() {
		List<OutputAllClientDTO> list = new ArrayList<>();
		list.add(mockOutputAllClientDTO(0));
		list.add(mockOutputAllClientDTO(1));
		list.add(mockOutputAllClientDTO(2));
		return list;
	}
	
	public static OutputAllClientDTO mockOutputAllClientDTO(int i) {
		OutputAllClientDTO mock = new OutputAllClientDTO();
		mock.setId(mockId(i));
		mock.setName(mockName(i));
		mock.setEmail(mockEmail(i));
		return mock;
	}
	
	public static Client mockClientWithoutPet() {
		return mockClientWithoutPet(0);
	}
	
	public static Client mockClientWithoutPet(int i) {
		Client mock = new Client();
		mock.setId(mockId(i));
		mock.setFirstName(mockFirstName(i));
		mock.setLastName(mockLastName(i));
		mock.setEmail(mockEmail(i));
		mock.setCpf(mockCpf(i));
		mock.setBirthDate(mockBirthDate(i));
		return mock;
	}
}
