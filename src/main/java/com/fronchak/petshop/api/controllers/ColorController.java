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

import com.fronchak.petshop.domain.dtos.color.InsertColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputAllColorDTO;
import com.fronchak.petshop.domain.dtos.color.OutputColorDTO;
import com.fronchak.petshop.domain.dtos.color.UpdateColorDTO;
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
	
	@GetMapping
	public ResponseEntity<Page<OutputAllColorDTO>> findAllPaged(Pageable pageable) {
		Page<OutputAllColorDTO> page = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<OutputColorDTO> save(@Valid @RequestBody InsertColorDTO insertDTO) {
		OutputColorDTO outputDTO = service.save(insertDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(outputDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(outputDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<OutputColorDTO> update(@Valid @RequestBody UpdateColorDTO  updateDTO, @PathVariable Long id) {
		OutputColorDTO outputDTO = service.update(updateDTO, id);
		return ResponseEntity.ok().body(outputDTO);
	}
}
