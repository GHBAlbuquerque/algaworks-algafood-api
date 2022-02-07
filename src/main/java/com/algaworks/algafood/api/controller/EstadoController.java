package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoAssembler;
import com.algaworks.algafood.api.model.entrada.EstadoEntradaDTO;
import com.algaworks.algafood.api.model.saida.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@Autowired
	private EstadoAssembler assembler;
	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.findAll();
	}
	
	
	@GetMapping("/{id}")
	public EstadoDTO buscar(@PathVariable long id) {
		var estado = cadastroEstadoService.buscar(id);
		return assembler.convertToModel(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoEntradaDTO estadoEntrada) {
		var estado = assembler.convertToEntity(estadoEntrada);
		var estadoSalvo = cadastroEstadoService.salvar(estado);
		return assembler.convertToModel(estadoSalvo);

	}

	@PutMapping("/{id}")
	public ResponseEntity<EstadoDTO> atualizar(@PathVariable long id, @RequestBody EstadoEntradaDTO estadoEntrada) {
		var estado = assembler.convertToEntity(estadoEntrada);
		var estadoExistente = cadastroEstadoService.buscar(id);

		BeanUtils.copyProperties(estado, estadoExistente, "id");
		estado = cadastroEstadoService.salvar(estadoExistente);
		return ResponseEntity.ok(assembler.convertToModel(estado));
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		cadastroEstadoService.remover(id);
		return ResponseEntity.noContent().build();
	}



}
