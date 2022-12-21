package com.fronchak.petshop.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fronchak.petshop.domain.dtos.client.OutputAllClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.services.ClientService;

@RestController
@RequestMapping(value = "/api/clients")
public class ClientController {

	@Autowired
	private ClientService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OutputClientDTO> findById(@PathVariable Long id) {
		OutputClientDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping
	public ResponseEntity<Page<OutputAllClientDTO>> findAllPaged(Pageable pageable) {
		Page<OutputAllClientDTO> page = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(page);
	}
}
