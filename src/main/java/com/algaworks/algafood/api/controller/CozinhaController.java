package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.CadastroCozinhaService;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public List<Cozinha> listarXML() {
		return cozinhaRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable long id) {
		var cozinha = cozinhaRepository.findById(id);

		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/por-nome")
	public ResponseEntity<List<Cozinha>> buscarPorNome(@PathParam(value = "nome") String nome) {
		var cozinha = cozinhaRepository.findByNomeContaining(nome);
		return ResponseEntity.ok(cozinha);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha salvar(@RequestBody Cozinha cozinha) {
		return cadastroCozinhaService.salvar(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable long id, @RequestBody Cozinha cozinha) {
		var cozinhaExistente = cozinhaRepository.findById(id);

		if (cozinhaExistente.isPresent()) {
			BeanUtils.copyProperties(cozinha, cozinhaExistente, "id");
			cozinha = cadastroCozinhaService.salvar(cozinhaExistente.get());
			return ResponseEntity.ok(cozinha);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		try {
			cadastroCozinhaService.remover(id);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

		} catch (EntidadeNaoEncontradaException e) { 
			return ResponseEntity.notFound().build();
		}
	}

}
