package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaAssembler;
import com.algaworks.algafood.api.model.input.CozinhaInputDTO;
import com.algaworks.algafood.api.model.output.CozinhaDTO;
import com.algaworks.algafood.api.openapi.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
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
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private CozinhaAssembler assembler;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<CozinhaDTO> listar() {
		var cozinhas = cozinhaRepository.findAll();
		return assembler.convertListToModel(cozinhas);
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public List<CozinhaDTO> listarXML() {
		var cozinhas = cozinhaRepository.findAll();
		return cozinhas.stream().map(cozinha -> assembler.convertToModel(cozinha)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public CozinhaDTO buscar(@PathVariable long id) {
		var cozinha = cozinhaService.buscar(id);
		return assembler.convertToModel(cozinha);

	}
	
	@GetMapping("/por-nome")
	public ResponseEntity<List<CozinhaDTO>> buscarPorNome(@PathParam(value = "nome") String nome) {
		var cozinhas = cozinhaRepository.findByNomeContaining(nome);
		var cozinhaModels = cozinhas.stream()
				.map(cozinha -> assembler.convertToModel(cozinha))
				.collect(Collectors.toList());
		return ResponseEntity.ok(cozinhaModels);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO salvar(@RequestBody @Valid CozinhaInputDTO cozinhaInput) {
		var cozinha = assembler.convertToEntity(cozinhaInput);
		cozinha = cozinhaService.salvar(cozinha);
		return assembler.convertToModel(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CozinhaDTO> atualizar(@PathVariable long id, @RequestBody @Valid CozinhaInputDTO cozinhaInput) {
		var cozinhaExistente = cozinhaService.buscar(id);
		assembler.copyToInstance(cozinhaInput, cozinhaExistente);

		var cozinha = cozinhaService.salvar(cozinhaExistente);
		return ResponseEntity.ok(assembler.convertToModel(cozinha));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		cozinhaService.remover(id);
		return ResponseEntity.noContent().build();
			
	}

}
