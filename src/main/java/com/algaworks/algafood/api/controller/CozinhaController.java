package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.entrada.CozinhaEntradaDTO;
import com.algaworks.algafood.api.model.saida.CozinhaDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<CozinhaDTO> listar() {
		var cozinhas = cozinhaRepository.findAll();

		return cozinhas.stream().map(this::convert).collect(Collectors.toList());
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public List<CozinhaDTO> listarXML() {
		var cozinhas = cozinhaRepository.findAll();
		return cozinhas.stream().map(this::convert).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public CozinhaDTO buscar(@PathVariable long id) {
		var cozinha = cadastroCozinhaService.buscar(id);
		return convert(cozinha);

	}
	
	@GetMapping("/por-nome")
	public ResponseEntity<List<CozinhaDTO>> buscarPorNome(@PathParam(value = "nome") String nome) {
		var cozinhas = cozinhaRepository.findByNomeContaining(nome);
		var cozinhaModels = cozinhas.stream()
				.map(this::convert)
				.collect(Collectors.toList());
		return ResponseEntity.ok(cozinhaModels);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO salvar(@RequestBody @Valid CozinhaEntradaDTO cozinhaEntrada) {
		var cozinhaRecebida = convert(cozinhaEntrada);
		var cozinha = cadastroCozinhaService.salvar(cozinhaRecebida);
		return convert(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CozinhaDTO> atualizar(@PathVariable long id, @RequestBody CozinhaEntradaDTO cozinhaEntrada) {
		var cozinhaRecebida = convert(cozinhaEntrada);
		var cozinhaExistente = cadastroCozinhaService.buscar(id);

		BeanUtils.copyProperties(cozinhaRecebida, cozinhaExistente, "id");
		var cozinha = cadastroCozinhaService.salvar(cozinhaExistente);
		return ResponseEntity.ok(convert(cozinha));
	
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		cadastroCozinhaService.remover(id);
		return ResponseEntity.noContent().build();
			
	}

	public CozinhaDTO convert(Cozinha cozinha) {
		try {
			var objectMapper = new ObjectMapper();
			return objectMapper.convertValue(cozinha, CozinhaDTO.class);
		} catch (IllegalArgumentException ex) {
			throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
		}
	}

	public Cozinha convert(CozinhaEntradaDTO cozinha) {
		try {
			var objectMapper = new ObjectMapper();
			return objectMapper.convertValue(cozinha, Cozinha.class);
		} catch (IllegalArgumentException ex) {
			throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.");
		}
	}

}
