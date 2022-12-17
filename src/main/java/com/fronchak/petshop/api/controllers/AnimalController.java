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

import com.fronchak.petshop.domain.dtos.animal.InsertAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAllAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.OutputAnimalDTO;
import com.fronchak.petshop.domain.dtos.animal.UpdateAnimalDTO;
import com.fronchak.petshop.domain.services.AnimalService;

@RestController
@RequestMapping(value = "/api/animals")
public class AnimalController {

	@Autowired
	private AnimalService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OutputAnimalDTO> findById(@PathVariable Long id) {
		OutputAnimalDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping
	public ResponseEntity<Page<OutputAllAnimalDTO>> findAllPaged(Pageable pageable) {
		Page<OutputAllAnimalDTO> page = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<OutputAnimalDTO> save(@Valid @RequestBody InsertAnimalDTO insertDTO) {
		OutputAnimalDTO outputDTO = service.save(insertDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(outputDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(outputDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<OutputAnimalDTO> update(@Valid @RequestBody UpdateAnimalDTO updateDTO, @PathVariable Long id) {
		OutputAnimalDTO outputDTO = service.update(updateDTO, id);
		return ResponseEntity.ok().body(outputDTO);
	}
}
