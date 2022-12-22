package com.fronchak.petshop.domain.dtos.client;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

public class InputClientDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Client's first name cannot be empty")
	private String firstName;
	
	@NotBlank(message = "Client's last name cannot be empty")
	private String lastName;
	
	@NotNull(message = "Client's email must be specified")
	@Email(message = "Invalid email format, please try a valid email", 
	regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
	private String email;
	
	@NotNull(message = "Client's CPF must be specified")
	@Pattern(regexp = "(\\d{11})", message = "Invalid cpf format, please try a valid cpf (only digits)")
	private String cpf;
	
	@NotNull(message = "Client's birth date must be specified")
	@Past(message = "Invalid birth date, birth date cannot be a future date")
	private LocalDate birthDate;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
}
