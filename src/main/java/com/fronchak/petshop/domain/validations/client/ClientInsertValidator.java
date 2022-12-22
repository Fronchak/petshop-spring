package com.fronchak.petshop.domain.validations.client;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fronchak.petshop.domain.dtos.client.InsertClientDTO;
import com.fronchak.petshop.domain.entities.Client;
import com.fronchak.petshop.domain.exceptions.FieldMessage;
import com.fronchak.petshop.domain.repositories.ClientRepository;

public class ClientInsertValidator implements ConstraintValidator<ClientInsertValid, InsertClientDTO> {

	@Autowired
	private ClientRepository repository;
	
	@Override
	public boolean isValid(InsertClientDTO dto, ConstraintValidatorContext context) {
	
		List<FieldMessage> errors = new ArrayList<>();
		
		Client entity = repository.findByEmail(dto.getEmail());
		if(entity != null) {
			errors.add(new FieldMessage("email", "Invalid email, this email was already registered"));
		}
		
		entity = repository.findByCpf(dto.getCpf());
		if(entity != null) {
			errors.add(new FieldMessage("cpf", "Invalid cpf, this cpf was already registered"));
		}
		
		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		return errors.isEmpty();
	}

}
