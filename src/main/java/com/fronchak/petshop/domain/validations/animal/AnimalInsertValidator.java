package com.fronchak.petshop.domain.validations.animal;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fronchak.petshop.domain.dtos.animal.InsertAnimalDTO;
import com.fronchak.petshop.domain.entities.Animal;
import com.fronchak.petshop.domain.exceptions.FieldMessage;
import com.fronchak.petshop.domain.repositories.AnimalRepository;

public class AnimalInsertValidator implements ConstraintValidator<AnimalInsertValid, InsertAnimalDTO> {

	@Autowired
	private AnimalRepository repository;
	
	@Override
	public boolean isValid(InsertAnimalDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> errors = new ArrayList<>();
		
		Animal entity = repository.findByName(dto.getName());
		if(entity != null) {
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
