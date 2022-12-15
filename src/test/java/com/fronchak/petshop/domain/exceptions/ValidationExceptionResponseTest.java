package com.fronchak.petshop.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ValidationExceptionResponseTest {

	private ValidationExceptionResponse response;
	String fieldName = "fieldName";
	String message = "message";
	
	@BeforeEach
	void setUp() {
		response = new ValidationExceptionResponse();
	}
	
	@Test
	public void addErrorShouldAddfieldMessageToErrosList() {
		FieldMessage fieldMessage = new FieldMessage(fieldName, message);
		response.addError(fieldMessage);
		
		FieldMessage result = response.getErrors().get(0);
		assertEquals(fieldName, result.getFieldName());
		assertEquals(message, result.getMessage());
	}
	
	@Test
	public void addErrorShouldCreateFieldMessageAndAddToErrors() {
		response.addError(fieldName, message);
		
		FieldMessage result = response.getErrors().get(0);
		assertEquals(fieldName, result.getFieldName());
		assertEquals(message, result.getMessage());
	}
}
