package com.fronchak.petshop.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.services.ColorService;

@RestController
@RequestMapping(value = "/api/colors")
public class ColorController {

	@Autowired
	private ColorService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OutputColorDTO> findById(@PathVariable Long id) {
		OutputColorDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
}
