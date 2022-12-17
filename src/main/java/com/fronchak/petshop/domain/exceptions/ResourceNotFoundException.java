package com.fronchak.petshop.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	
	public ResourceNotFoundException(String entity, Long id) {
		super(entity + " not found by ID: " + id);
	}

	public static String getError() {
		return "Resource not found";
	}

}
