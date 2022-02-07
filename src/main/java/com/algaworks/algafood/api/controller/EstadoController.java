package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.entrada.EstadoEntradaDTO;
import com.algaworks.algafood.api.model.saida.EstadoDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.findAll();
	}
	
	
	@GetMapping("/{id}")
	public EstadoDTO buscar(@PathVariable long id) {
		var estado = cadastroEstadoService.buscar(id);
		return convert(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoEntradaDTO estadoEntrada) {
		var estado = convert(estadoEntrada);
		var estadoSalvo = cadastroEstadoService.salvar(estado);
		return convert(estadoSalvo);

	}

	@PutMapping("/{id}")
	public ResponseEntity<EstadoDTO> atualizar(@PathVariable long id, @RequestBody EstadoEntradaDTO estadoEntrada) {
		var estado = convert(estadoEntrada);
		var estadoExistente = cadastroEstadoService.buscar(id);

		BeanUtils.copyProperties(estado, estadoExistente, "id");
		estado = cadastroEstadoService.salvar(estadoExistente);
		return ResponseEntity.ok(convert(estado));
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		cadastroEstadoService.remover(id);
		return ResponseEntity.noContent().build();
	}

	public EstadoDTO convert(Estado estado) {
		try {
			var objectMapper = new ObjectMapper();
			return objectMapper.convertValue(estado, EstadoDTO.class);
		} catch (IllegalArgumentException ex) {
			throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
		}
	}

	public Estado convert(EstadoEntradaDTO estado) {
		try {
			var objectMapper = new ObjectMapper();
			return objectMapper.convertValue(estado, Estado.class);
		} catch (IllegalArgumentException ex) {
			throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.");
		}
	}

}
