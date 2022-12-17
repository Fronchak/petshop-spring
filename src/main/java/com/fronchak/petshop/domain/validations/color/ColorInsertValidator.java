package com.fronchak.petshop.domain.validations.color;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fronchak.petshop.domain.dtos.color.InsertColorDTO;
import com.fronchak.petshop.domain.entities.Color;
import com.fronchak.petshop.domain.exceptions.FieldMessage;
import com.fronchak.petshop.domain.repositories.ColorRepository;

public class ColorInsertValidator implements ConstraintValidator<ColorInsertValid, InsertColorDTO> {

	@Autowired
	private ColorRepository repository;
	
	@Override
	public boolean isValid(InsertColorDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> errors = new ArrayList<>();
		
		Color color = repository.findByName(dto.getName());
		if(color != null) {
			errors.add(new FieldMessage("name", "There is another color with the same name saved"));
		}
		
		color = repository.findByRgb(dto.getRgb());
		if(color != null) {
			errors.add(new FieldMessage("rgb", "There is another color with the same rgb saved"));
		}
		
		color = repository.findByHex(dto.getHex());
		if(color != null) {
			errors.add(new FieldMessage("hex", "There is another color with the same hex saved"));
		}
		
		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		return errors.isEmpty();
	}


}
