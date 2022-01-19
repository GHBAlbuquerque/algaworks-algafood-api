package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.CadastroRestauranteService;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable long id) {
		var restaurante = restauranteRepository.findById(id);

		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/por-nome-e-id-cozinha") //query no orm.xml
	public ResponseEntity<List<Restaurante>> buscarPorNomeECozinha(@PathParam(value = "nome") String nome, @PathParam(value = "cozinha_id") Long cozinhaId) {
		var restaurante = restauranteRepository.consultarPorNomeECozinha(nome, cozinhaId);
		return ResponseEntity.ok(restaurante);
	}
	
	@GetMapping("/por-nome")
	public ResponseEntity<Restaurante> buscarPorNome(@PathParam(value = "nome") String nome) {
		var restaurante = restauranteRepository.findFirstRestauranteByNomeContaining(nome);
		if(restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/count-por-cozinhaId")
	public int quantidadePorCozinhaId(Long cozinhaId){
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	//SPECIFICATION
	@GetMapping("/specification")
	public List<Restaurante> queryPorSpecification(String nome){
		
		return restauranteRepository.buscarComFreteGratis(nome);
	}
	

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable long id, @RequestBody Restaurante restaurante) {
		
		var restauranteExistente = restauranteRepository.findById(id);

		if (restauranteExistente != null) {
			try {
				BeanUtils.copyProperties(restaurante, restauranteExistente, 
						"id", "formasPagamento", "endereco");
				restaurante = restauranteRepository.save(restauranteExistente.get());
				return ResponseEntity.ok(restauranteExistente);
			
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		try {
			cadastroRestauranteService.remover(id);
			return ResponseEntity.noContent().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable long id, @RequestBody Map<String, Object> campos) {
		try {
			var restaurante = cadastroRestauranteService.atualizarParcial(id, campos);
			return ResponseEntity.ok(restaurante);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
