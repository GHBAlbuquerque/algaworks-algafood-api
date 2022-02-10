package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteAssembler;
import com.algaworks.algafood.api.model.entrada.RestauranteEntradaDTO;
import com.algaworks.algafood.api.model.saida.RestauranteDTO;
import com.algaworks.algafood.api.model.saida.RestauranteSingletonDTO;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.entitynotfound.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.validation.OrderedChecksTaxaFrete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteAssembler assembler;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<RestauranteDTO> listar() {
        var restaurantes = restauranteRepository.findAll();
        return restaurantes.stream().map(restaurante -> assembler.convertToModel(restaurante)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RestauranteSingletonDTO buscar(@PathVariable long id) {
        var restaurante = restauranteService.buscar(id);
        return assembler.convertToSingletonModel(restaurante);
    }

    @GetMapping("/por-nome-e-id-cozinha") // query no orm.xml
    public ResponseEntity<List<RestauranteDTO>> buscarPorNomeECozinha(@PathParam(value = "nome") String nome,
                                                                      @PathParam(value = "cozinha_id") Long cozinhaId) {
        var restaurantes = restauranteRepository.consultarPorNomeECozinha(nome, cozinhaId);
        return ResponseEntity.ok(
                restaurantes.stream().map(restaurante -> assembler.convertToModel(restaurante))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/por-nome")
    public ResponseEntity<RestauranteDTO> buscarPorNome(@PathParam(value = "nome") String nome) {
        var restaurante = restauranteRepository.findFirstRestauranteByNomeContaining(nome);
        if (restaurante.isPresent()) {
            var restauranteDTO = assembler.convertToModel(restaurante.get());
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
        return restaurantes.stream().map(restaurante -> assembler.convertToModel(restaurante)).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteSingletonDTO adicionar(@RequestBody @Validated({OrderedChecksTaxaFrete.class, Default.class}) RestauranteEntradaDTO restauranteEntrada) {
        var restaurante = assembler.convertToEntity(restauranteEntrada);
        try {
            var restauranteSalvo = restauranteService.salvar(restaurante);
            return assembler.convertToSingletonModel(restauranteSalvo);
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteSingletonDTO> atualizar(@PathVariable long id, @RequestBody @Validated({OrderedChecksTaxaFrete.class, Default.class}) RestauranteEntradaDTO restauranteEntrada) {
        try {
            var restauranteExistente = restauranteService.buscar(id);
            assembler.copyToInstance(restauranteEntrada, restauranteExistente);

            var restauranteSalvo = restauranteService.salvar(restauranteExistente);
            return ResponseEntity.ok(assembler.convertToSingletonModel(restauranteSalvo));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        restauranteService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestauranteSingletonDTO> atualizarParcial(@PathVariable long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {

        var restaurante = restauranteService.atualizarParcial(id, campos, request);
        return ResponseEntity.ok(assembler.convertToSingletonModel(restaurante));
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable long id) {
        restauranteService.ativar(id);
    }

    @PutMapping("/{id}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable long id) {
        restauranteService.desativar(id);
    }

}
