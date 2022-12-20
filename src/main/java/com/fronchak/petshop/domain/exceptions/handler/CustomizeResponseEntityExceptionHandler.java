package com.fronchak.petshop.domain.exceptions.handler;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.fronchak.petshop.domain.exceptions.DatabaseException;
import com.fronchak.petshop.domain.exceptions.DatabaseReferenceException;
import com.fronchak.petshop.domain.exceptions.ExceptionResponse;
import com.fronchak.petshop.domain.exceptions.ResourceNotFoundException;
import com.fronchak.petshop.domain.exceptions.ValidationException;
import com.fronchak.petshop.domain.exceptions.ValidationExceptionResponse;

@RestControllerAdvice
public class CustomizeResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(
			ResourceNotFoundException e, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ExceptionResponse response = makeResponse(e, request, status, ResourceNotFoundException.getError());
		return ResponseEntity.status(status).body(response);
	}
	
	public ExceptionResponse makeResponse(Exception e, WebRequest request, 
			HttpStatus status, String error) {
		ExceptionResponse response = new ExceptionResponse();
		response.setTimestamp(Instant.now());
		response.setStatus(status.value());
		response.setError(error);
		response.setMessage(e.getMessage());
		response.setPath(request.getDescription(false));
		return response;
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<ExceptionResponse> handleDatabaseException(DatabaseException e, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ExceptionResponse response = makeResponse(e, request, status, DatabaseException.getError());
		return ResponseEntity.status(status).body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationExceptionResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException e, WebRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationExceptionResponse response = new ValidationExceptionResponse();
		response.setTimestamp(Instant.now());
		response.setError("Validation error");
		response.setStatus(status.value());
		response.setMessage(e.getMessage());
		response.setPath(request.getDescription(false));
		
		for(FieldError field : e.getBindingResult().getFieldErrors()) {
			response.addError(field.getField(), field.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(response);
	}
	
	@ExceptionHandler(DatabaseReferenceException.class)
	public ResponseEntity<ExceptionResponse> handleDatabaseReferenceException(DatabaseReferenceException e, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ExceptionResponse response = makeResponse(e, request, status, DatabaseReferenceException.getError());
		return ResponseEntity.status(status).body(response);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ValidationExceptionResponse> handleValidationExceptionResponse(
			ValidationException e, WebRequest request
			) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ValidationExceptionResponse response = new ValidationExceptionResponse();
		response.setTimestamp(Instant.now());
		response.setError("Validation error");
		response.setStatus(status.value());
		response.setMessage(e.getMessage());
		response.setPath(request.getDescription(false));
		response.addError(e.getFieldMessage());
		return ResponseEntity.status(status).body(response);
	}
	
}
