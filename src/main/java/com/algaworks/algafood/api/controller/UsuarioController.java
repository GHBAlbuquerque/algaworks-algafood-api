package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.model.input.update.SenhaUpdateDTO;
import com.algaworks.algafood.api.model.input.update.UsuarioUpdateDTO;
import com.algaworks.algafood.api.model.output.UsuarioDTO;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioAssembler assembler;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<UsuarioDTO> listar(Pageable pageable) {
		var usuariosPage = usuarioRepository.findAll(pageable);
		var usuarios = assembler.convertListToModel(usuariosPage.getContent());
		return new PageImpl<>(usuarios, pageable, usuariosPage.getTotalElements());
	}

	@GetMapping("/{id}")
	public UsuarioDTO buscar(@PathVariable long id) {
		var usuario = usuarioService.buscar(id);
		return assembler.convertToModel(usuario);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO salvar(@RequestBody @Valid UsuarioInputDTO usuarioInput) {
		var usuario = assembler.convertToEntity(usuarioInput);
		usuario = usuarioService.salvar(usuario);
		return assembler.convertToModel(usuario);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable long id, @RequestBody @Valid UsuarioUpdateDTO usuarioInput) {
		var usuarioExistente = usuarioService.buscar(id);
		assembler.copyToInstance(usuarioInput, usuarioExistente);

		var usuario = usuarioService.salvar(usuarioExistente);
		return ResponseEntity.ok(assembler.convertToModel(usuario));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		usuarioService.remover(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/senha")
	public ResponseEntity<UsuarioDTO> alterarSenha(@PathVariable long id, @RequestBody @Valid SenhaUpdateDTO senha) {

		usuarioService.trocarSenha(id, senha);
		return ResponseEntity.noContent().build();
	}
}
