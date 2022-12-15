package com.fronchak.petshop.domain.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.fronchak.petshop.domain.dtos.color.InsertColorDTO;
import com.fronchak.petshop.domain.dtos.color.UpdateColorDTO;
import com.fronchak.petshop.domain.entities.Color;
import com.fronchak.petshop.domain.exceptions.FieldMessage;
import com.fronchak.petshop.domain.repositories.ColorRepository;

public class ColorUpdateValidator implements ConstraintValidator<ColorUpdateValid, UpdateColorDTO> {

	@Autowired
	private ColorRepository repository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public boolean isValid(UpdateColorDTO dto, ConstraintValidatorContext context) {
		
		Map<String, String> uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long id = Long.parseLong(uriVars.get("id"));
		
		List<FieldMessage> errors = new ArrayList<>();
		
		Color color = repository.findByName(dto.getName());
		if(color != null &&  !color.getId().equals(id)) {
			errors.add(new FieldMessage("name", "There is another color with the same name saved"));
		}
		
		color = repository.findByRgb(dto.getRgb());
		if(color != null && !color.getId().equals(id)) {
			errors.add(new FieldMessage("rgb", "There is another color with the same rgb saved"));
		}
		
		color = repository.findByHex(dto.getHex());
		if(color != null && !color.getId().equals(id)) {
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
