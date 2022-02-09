package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.model.entrada.SenhaEntradaDTO;
import com.algaworks.algafood.api.model.entrada.UsuarioEntradaDTO;
import com.algaworks.algafood.api.model.entrada.UsuarioNovoEntradaDTO;
import com.algaworks.algafood.api.model.saida.UsuarioDTO;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<UsuarioDTO> listar() {
		var usuarios = usuarioRepository.findAll();

		return usuarios.stream().map(usuario -> assembler.convertToModel(usuario)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public UsuarioDTO buscar(@PathVariable long id) {
		var usuario = usuarioService.buscar(id);
		return assembler.convertToModel(usuario);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO salvar(@RequestBody @Valid UsuarioNovoEntradaDTO usuarioEntrada) {
		var usuarioRecebida = assembler.convertToEntity(usuarioEntrada);
		var usuario = usuarioService.salvar(usuarioRecebida);
		return assembler.convertToModel(usuario);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable long id, @RequestBody @Valid UsuarioEntradaDTO usuarioEntrada) {
		var usuarioExistente = usuarioService.buscar(id);
		assembler.copyToInstance(usuarioEntrada, usuarioExistente);

		var usuario = usuarioService.salvar(usuarioExistente);
		return ResponseEntity.ok(assembler.convertToModel(usuario));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		usuarioService.remover(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/senha")
	public ResponseEntity<UsuarioDTO> alterarSenha(@PathVariable long id, @RequestBody @Valid SenhaEntradaDTO senha) {

		usuarioService.trocarSenha(id, senha);
		return ResponseEntity.noContent().build();
	}
}
