package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeAssembler;
import com.algaworks.algafood.api.assembler.CozinhaAssembler;
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

	@Autowired
	private CozinhaAssembler assembler;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<CozinhaDTO> listar() {
		var cozinhas = cozinhaRepository.findAll();

		return cozinhas.stream().map(cozinha -> assembler.convert(cozinha)).collect(Collectors.toList());
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public List<CozinhaDTO> listarXML() {
		var cozinhas = cozinhaRepository.findAll();
		return cozinhas.stream().map(cozinha -> assembler.convert(cozinha)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public CozinhaDTO buscar(@PathVariable long id) {
		var cozinha = cadastroCozinhaService.buscar(id);
		return assembler.convert(cozinha);

	}
	
	@GetMapping("/por-nome")
	public ResponseEntity<List<CozinhaDTO>> buscarPorNome(@PathParam(value = "nome") String nome) {
		var cozinhas = cozinhaRepository.findByNomeContaining(nome);
		var cozinhaModels = cozinhas.stream()
				.map(cozinha -> assembler.convert(cozinha))
				.collect(Collectors.toList());
		return ResponseEntity.ok(cozinhaModels);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO salvar(@RequestBody @Valid CozinhaEntradaDTO cozinhaEntrada) {
		var cozinhaRecebida = assembler.convert(cozinhaEntrada);
		var cozinha = cadastroCozinhaService.salvar(cozinhaRecebida);
		return assembler.convert(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CozinhaDTO> atualizar(@PathVariable long id, @RequestBody CozinhaEntradaDTO cozinhaEntrada) {
		var cozinhaRecebida = assembler.convert(cozinhaEntrada);
		var cozinhaExistente = cadastroCozinhaService.buscar(id);

		BeanUtils.copyProperties(cozinhaRecebida, cozinhaExistente, "id");
		var cozinha = cadastroCozinhaService.salvar(cozinhaExistente);
		return ResponseEntity.ok(assembler.convert(cozinha));
	
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		cadastroCozinhaService.remover(id);
		return ResponseEntity.noContent().build();
			
	}

}
