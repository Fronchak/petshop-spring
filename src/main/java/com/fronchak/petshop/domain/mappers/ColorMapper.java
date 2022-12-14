package com.fronchak.petshop.domain.mappers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fronchak.petshop.domain.dtos.color.InputColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputAllColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.entities.Color;

@Service
public class ColorMapper {

	public void copyInputDTOToEntity(InputColorDTO dto, Color entity) {
		entity.setName(dto.getName());
		entity.setRgb(dto.getRgb());
		entity.setHex(dto.getHex());
	}
	
	public OutputColorDTO convertEntityToOutputDTO(Color entity) {
		OutputColorDTO dto = new OutputColorDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setRgb(entity.getRgb());
		dto.setHex(entity.getHex());
		return dto;
	}
	
	public Page<OutputAllColorDTO> convertEntityPageToOutputAllDTOPage(Page<Color> page) {
		return page.map(entity -> convertEntityToOutputAllDTO(entity));
	}
	
	public OutputAllColorDTO convertEntityToOutputAllDTO(Color entity) {
		OutputAllColorDTO dto = new OutputAllColorDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
}
