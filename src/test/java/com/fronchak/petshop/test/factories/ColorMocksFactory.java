package com.fronchak.petshop.test.factories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fronchak.petshop.domain.dtos.color.InsertColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.dtos.color.UpdateColorDTO;
import com.fronchak.petshop.domain.entities.Color;

public class ColorMocksFactory {

	public static InsertColorDTO mockInsertColorDTO() {
		return mockInsertColorDTO(0);
	}
	
	public static InsertColorDTO mockInsertColorDTO(int i) {
		InsertColorDTO mock = new InsertColorDTO();
		mock.setName(mockName(i));
		mock.setRgb(mockRgb(i));
		mock.setHex(mockHex(i));
		return mock;
	}
	
	private static String mockName(int i) {
		return "Mock name " + i;
	}
	
	private static String mockRgb(int i) {
		return "Mock rgb " + i;
	}
	
	private static String mockHex(int i) {
		return "Mock hex " + i;
	}
	
	public static Color mockColor() {
		return mockColor(0);
	}
	
	public static Color mockColor(int i) {
		Color mock = new Color();
		mock.setId(mockId(i));
		mock.setName(mockName(i));
		mock.setRgb(mockRgb(i));
		mock.setHex(mockHex(i));
		return mock;
	}
	
	private static Long mockId(int i) {
		return i + 0L;
	}
	
	public static UpdateColorDTO mockUpdateColorDTO() {
		return mockUpdateColorDTO(0);
	}
	
	public static UpdateColorDTO mockUpdateColorDTO(int i) {
		UpdateColorDTO mock = new UpdateColorDTO();
		mock.setName(mockName(i));
		mock.setRgb(mockRgb(i));
		mock.setHex(mockHex(i));
		return mock;
	}
	
	public static OutputColorDTO mockOutputColorDTO() {
		return mockOutputColorDTO(0);
	}
	
	public static OutputColorDTO mockOutputColorDTO(int i) {
		OutputColorDTO mock = new OutputColorDTO();
		mock.setId(mockId(i));
		mock.setName(mockName(i));
		mock.setRgb(mockRgb(i));
		mock.setHex(mockHex(i));
		return mock;
	}
	
	public static Page<Color> mockColorPage() {
		return new PageImpl<>(mockColorList());
	}
	
	public static List<Color> mockColorList() {
		List<Color> list = new ArrayList<>();
		list.add(mockColor(0));
		list.add(mockColor(1));
		list.add(mockColor(2));
		return list;
	}
}