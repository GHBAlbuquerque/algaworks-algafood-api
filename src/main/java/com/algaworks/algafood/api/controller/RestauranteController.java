package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.validation.OrderedChecksTaxaFrete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.groups.Default;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

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
	public Restaurante buscar(@PathVariable long id) {
		return cadastroRestauranteService.buscar(id);
	}

	@GetMapping("/por-nome-e-id-cozinha") // query no orm.xml
	public ResponseEntity<List<Restaurante>> buscarPorNomeECozinha(@PathParam(value = "nome") String nome,
			@PathParam(value = "cozinha_id") Long cozinhaId) {
		var restaurante = restauranteRepository.consultarPorNomeECozinha(nome, cozinhaId);
		return ResponseEntity.ok(restaurante);
	}

	@GetMapping("/por-nome")
	public ResponseEntity<Restaurante> buscarPorNome(@PathParam(value = "nome") String nome) {
		var restaurante = restauranteRepository.findFirstRestauranteByNomeContaining(nome);
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/count-por-cozinhaId")
	public int quantidadePorCozinhaId(Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}

	// SPECIFICATION
	@GetMapping("/specification")
	public List<Restaurante> queryPorSpecification(String nome) {
		return restauranteRepository.buscarComFreteGratis(nome);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Validated({OrderedChecksTaxaFrete.class, Default.class}) Restaurante restaurante) {
		try {
			return cadastroRestauranteService.salvar(restaurante);
		} catch (CozinhaNaoEncontradaException ex) {
			var idCozinha = restaurante.getCozinha().getId();
			throw new EntidadeReferenciadaInexistenteException(Cozinha.class, idCozinha);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable long id, @RequestBody Restaurante restaurante) {

		var restauranteExistente = cadastroRestauranteService.buscar(id);

		BeanUtils.copyProperties(restaurante, restauranteExistente, "id", "formasPagamento", "endereco", "dataCadastro",
				"produtos", "responsaveis");

		try {
			restaurante = cadastroRestauranteService.salvar(restauranteExistente);
			return ResponseEntity.ok(restauranteExistente);
		} catch (CozinhaNaoEncontradaException ex) {
			throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		cadastroRestauranteService.remover(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {

		var restaurante = cadastroRestauranteService.atualizarParcial(id, campos, request);
		return ResponseEntity.ok(restaurante);

	}

}
