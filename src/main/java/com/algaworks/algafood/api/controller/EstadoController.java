package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoAssembler;
import com.algaworks.algafood.api.model.input.EstadoInputDTO;
import com.algaworks.algafood.api.model.saida.EstadoDTO;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoService estadoService;

	@Autowired
	private EstadoAssembler assembler;
	
	@GetMapping
	public List<EstadoDTO> listar(){
		var estados = estadoRepository.findAll();
		return estados.stream().map(estado -> assembler.convertToModel(estado)).collect(Collectors.toList());
	}
	
	
	@GetMapping("/{id}")
	public EstadoDTO buscar(@PathVariable long id) {
		var estado = estadoService.buscar(id);
		return assembler.convertToModel(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInputDTO estadoInput) {
		var estado = assembler.convertToEntity(estadoInput);
		var estadoSalvo = estadoService.salvar(estado);
		return assembler.convertToModel(estadoSalvo);

	}

	@PutMapping("/{id}")
	public ResponseEntity<EstadoDTO> atualizar(@PathVariable long id, @RequestBody @Valid EstadoInputDTO estadoInput) {
		var estadoExistente = estadoService.buscar(id);
		assembler.copyToInstance(estadoInput, estadoExistente);

		var estado = estadoService.salvar(estadoExistente);
		return ResponseEntity.ok(assembler.convertToModel(estado));
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		estadoService.remover(id);
		return ResponseEntity.noContent().build();
	}



}
