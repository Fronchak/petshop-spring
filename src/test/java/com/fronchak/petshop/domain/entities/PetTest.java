package com.fronchak.petshop.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fronchak.petshop.domain.exceptions.FieldMessage;
import com.fronchak.petshop.domain.exceptions.ValidationException;
import com.fronchak.petshop.test.factories.ColorMocksFactory;

@ExtendWith(SpringExtension.class)
public class PetTest {

	private Pet pet;
	
	@BeforeEach
	void setUp() {
		pet = new Pet();
	}
	
	@Test
	public void addColorShouldAddColorToColorListWhenListIsEmpty() {
		Color color = ColorMocksFactory.mockColor();
		pet.addColor(color);
		
		List<Color> resultList = pet.getColors();
		Color result = resultList.get(0);
		
		assertEquals(0L, result.getId());
		assertEquals("Mock color name 0", result.getName());
	}
	
	@Test
	public void addColorShouldAddColorToColorListWhenThereIsNoOtherColorWithTheSameIdInTheList() {
		Color color1 = ColorMocksFactory.mockColor(0);
		Color color2 = ColorMocksFactory.mockColor(1);
		
		pet.addColor(color1);
		pet.addColor(color2);
		
		List<Color> resultList = pet.getColors();
		
		Color result = resultList.get(0);
		assertEquals(0L, result.getId());
		assertEquals("Mock color name 0", result.getName());
		
		result = resultList.get(1);
		assertEquals(1L, result.getId());
		assertEquals("Mock color name 1", result.getName());
	}
	
	@Test
	public void addColorShouldThrowValidationExceptionWhenThereIsAnotherColorWithTheSameIdAlreadyOnTheList() {
		Color color1 = ColorMocksFactory.mockColor();
		Color color2 = ColorMocksFactory.mockColor();
		
		pet.addColor(color1);
		
		ValidationException exception = assertThrows(ValidationException.class, () -> pet.addColor(color2));
		FieldMessage fieldMessage = exception.getFieldMessage();
		
		assertEquals("Validation error", exception.getMessage());
		assertEquals("colors", fieldMessage.getFieldName());
		assertEquals("Pet's colors cannot be duplicate", fieldMessage.getMessage());
	}
}
