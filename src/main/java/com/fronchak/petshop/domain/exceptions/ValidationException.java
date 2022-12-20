package com.fronchak.petshop.domain.exceptions;

public class ValidationException extends RuntimeException {

	private final FieldMessage fieldMessage;
	
	private static final long serialVersionUID = 1L;
	
	public ValidationException(String msg, FieldMessage fieldMessage) {
		super(msg);
		this.fieldMessage = fieldMessage;
	}
	
	public FieldMessage getFieldMessage() {
		return fieldMessage;
	}

	public static String getError() {
		return "Validation error";
	}
}
