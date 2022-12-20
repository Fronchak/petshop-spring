package com.fronchak.petshop.domain.exceptions;

public class DatabaseReferenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DatabaseReferenceException(String msg) {
		super(msg);
	}

	public static String getError() {
		return "Integrity reference error";
	}
}
