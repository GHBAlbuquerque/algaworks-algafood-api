package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoAssembler;
import com.algaworks.algafood.api.model.input.PermissaoInputDTO;
import com.algaworks.algafood.api.model.output.PermissaoDTO;
import com.algaworks.algafood.api.openapi.PermissaoControllerOpenApi;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import com.algaworks.algafood.domain.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController implements PermissaoControllerOpenApi {

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private PermissaoAssembler assembler;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<PermissaoDTO> listar() {
		var permissoes = permissaoRepository.findAll();
		return assembler.toCollectionModel(permissoes);
	}

	@GetMapping("/{id}")
	public PermissaoDTO buscar(@PathVariable long id) {
		var permissao = permissaoService.buscar(id);
		return assembler.toModel(permissao);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public PermissaoDTO salvar(@RequestBody @Valid PermissaoInputDTO permissaoInput) {
		var permissao = assembler.toEntity(permissaoInput);
		permissao = permissaoService.salvar(permissao);
		return assembler.toModel(permissao);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PermissaoDTO> atualizar(@PathVariable long id, @RequestBody @Valid PermissaoInputDTO permissaoInput) {
		var permissaoExistente = permissaoService.buscar(id);
		assembler.copyToInstance(permissaoInput, permissaoExistente);

		var permissao = permissaoService.salvar(permissaoExistente);
		return ResponseEntity.ok(assembler.toModel(permissao));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		permissaoService.remover(id);
		return ResponseEntity.noContent().build();
	}

}
