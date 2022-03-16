package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.v1.model.output.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.api.v1.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/restaurantes/{idRestaurante}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoAssembler assembler;

    @Autowired
    private LinkGenerator linkGenerator;

    @GetMapping()
    public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long idRestaurante) {
        var restaurante = restauranteService.buscar(idRestaurante);
        var models = assembler.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(linkGenerator.linkToFormasPagamentoRestaurante(idRestaurante).withSelfRel());

        models.forEach(model -> model.add(linkGenerator.linkToFormasPagamentoRestauranteRemover(
                idRestaurante,
                model.getId())));

        return models.add(linkGenerator.linkToFormasPagamentoRestauranteAdicionar(idRestaurante));
    }

    @PutMapping("/{idFormaPagamento}")
    public ResponseEntity<Void> adicionar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
        try {
            restauranteService.adicionarFormaPagamento(idRestaurante, idFormaPagamento);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @DeleteMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
        try {
            restauranteService.removerFormaPagamento(idRestaurante, idFormaPagamento);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }
}
