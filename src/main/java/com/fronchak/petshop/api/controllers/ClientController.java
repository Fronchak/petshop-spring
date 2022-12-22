package com.fronchak.petshop.api.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fronchak.petshop.domain.dtos.client.InsertClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputAllClientDTO;
import com.fronchak.petshop.domain.dtos.client.OutputClientDTO;
import com.fronchak.petshop.domain.dtos.client.UpdateClientDTO;
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
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<OutputClientDTO> save(@Valid @RequestBody InsertClientDTO insertDTO) {
		OutputClientDTO outputDTO = service.save(insertDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(outputDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(outputDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<OutputClientDTO> update(@Valid @RequestBody UpdateClientDTO updateDTO, @PathVariable Long id) {
		OutputClientDTO outputDTO = service.update(updateDTO, id);
		return ResponseEntity.ok().body(outputDTO);
	}
}
