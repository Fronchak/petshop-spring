package com.fronchak.petshop.domain.validations.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.fronchak.petshop.domain.dtos.client.UpdateClientDTO;
import com.fronchak.petshop.domain.entities.Client;
import com.fronchak.petshop.domain.exceptions.FieldMessage;
import com.fronchak.petshop.domain.repositories.ClientRepository;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdateValid, UpdateClientDTO> {

	@Autowired
	private ClientRepository repository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public boolean isValid(UpdateClientDTO dto, ConstraintValidatorContext context) {
	
		List<FieldMessage> errors = new ArrayList<>();
		
		Map<String, String> uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long id = Long.parseLong(uriVars.get("id"));
		
		Client entity = repository.findByEmail(dto.getEmail());
		if(entity != null && !entity.getId().equals(id)) {
			errors.add(new FieldMessage("email", "Invalid email, this email was already registered"));
		}
		
		entity = repository.findByCpf(dto.getCpf());
		if(entity != null && !entity.getId().equals(id)) {
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
