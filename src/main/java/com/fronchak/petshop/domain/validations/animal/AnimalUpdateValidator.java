package com.fronchak.petshop.domain.validations.animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.fronchak.petshop.domain.dtos.animal.InsertAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.UpdateAnimalDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.domain.exceptions.FieldMessage;
import com.fronchak.petshop.domain.repositories.AnimalRepository;

public class AnimalUpdateValidator implements ConstraintValidator<AnimalUpdateValid, UpdateAnimalDTO> {

	@Autowired
	private AnimalRepository repository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public boolean isValid(UpdateAnimalDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> errors = new ArrayList<>();
		
		Map<String, String> uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long id = Long.parseLong(uriVars.get("id"));
		
		Animal entity = repository.findByName(dto.getName());
		if(entity != null && !entity.getId().equals(id)) {
			errors.add(new FieldMessage("name", "There is another animal with the same name already registered"));
		}
		
		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		return errors.isEmpty();
	}

}
