package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.entrada.RestauranteEntradaDTO;
import com.algaworks.algafood.api.model.saida.RestauranteDTO;
import com.algaworks.algafood.api.model.saida.RestauranteDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.validation.OrderedChecksTaxaFrete;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<RestauranteDTO> listar() {
		var restaurantes = restauranteRepository.findAll();
		return restaurantes.stream().map(this::convert).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable long id) {
		var restaurante = cadastroRestauranteService.buscar(id);
		return convert(restaurante);
	}

	@GetMapping("/por-nome-e-id-cozinha") // query no orm.xml
	public ResponseEntity<List<RestauranteDTO>> buscarPorNomeECozinha(@PathParam(value = "nome") String nome,
			@PathParam(value = "cozinha_id") Long cozinhaId) {
		var restaurantes = restauranteRepository.consultarPorNomeECozinha(nome, cozinhaId);
		return ResponseEntity.ok(
				restaurantes.stream().map(this::convert)
						.collect(Collectors.toList()));
	}

	@GetMapping("/por-nome")
	public ResponseEntity<RestauranteDTO> buscarPorNome(@PathParam(value = "nome") String nome) {
		var restaurante = restauranteRepository.findFirstRestauranteByNomeContaining(nome);
		if (restaurante.isPresent()) {
			var restauranteDTO= convert(restaurante.get());
			return ResponseEntity.ok(restauranteDTO);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/count-por-cozinhaId")
	public int quantidadePorCozinhaId(Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}

	// SPECIFICATION
	@GetMapping("/specification")
	public List<RestauranteDTO> queryPorSpecification(String nome) {
		var restaurantes = restauranteRepository.buscarComFreteGratis(nome);
		return restaurantes.stream().map(this::convert).collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Validated({OrderedChecksTaxaFrete.class, Default.class}) RestauranteEntradaDTO restauranteEntrada) {
		var restaurante = convert(restauranteEntrada);
		try {
			var restauranteSalvo = cadastroRestauranteService.salvar(restaurante);
			return convert(restauranteSalvo);
		} catch (CozinhaNaoEncontradaException ex) {
			var idCozinha = restaurante.getCozinha().getId();
			throw new EntidadeReferenciadaInexistenteException(Cozinha.class, idCozinha);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<RestauranteDTO> atualizar(@PathVariable long id, @RequestBody @Validated({OrderedChecksTaxaFrete.class, Default.class}) RestauranteEntradaDTO restauranteEntrada) {
		var restaurante = convert(restauranteEntrada);
		var restauranteExistente = cadastroRestauranteService.buscar(id);

		BeanUtils.copyProperties(restaurante, restauranteExistente, "id", "formasPagamento", "dataCadastro",
				"produtos", "responsaveis");

		try {
			var restauranteSalvo = cadastroRestauranteService.salvar(restauranteExistente);
			return ResponseEntity.ok(convert(restauranteSalvo));
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
	public ResponseEntity<RestauranteDTO> atualizarParcial(@PathVariable long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {

		var restaurante = cadastroRestauranteService.atualizarParcial(id, campos, request);
		return ResponseEntity.ok(convert(restaurante));

	}

	public RestauranteDTO convert(Restaurante restaurante) {
		try {
			var objectMapper = new ObjectMapper();
			objectMapper.findAndRegisterModules();
			return objectMapper.convertValue(restaurante, RestauranteDTO.class);
		} catch (IllegalArgumentException ex) {
			throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.", ex.getCause());
		}
	}

	public Restaurante convert(RestauranteEntradaDTO restaurante) {
		try {
			var objectMapper = new ObjectMapper();
			objectMapper.findAndRegisterModules();
			return objectMapper.convertValue(restaurante, Restaurante.class);
		} catch (IllegalArgumentException ex) {
			throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
		}
	}

}
