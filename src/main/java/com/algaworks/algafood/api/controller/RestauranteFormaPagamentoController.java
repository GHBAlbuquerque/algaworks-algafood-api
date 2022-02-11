package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.model.output.FormaPagamentoDTO;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoAssembler assembler;

    @GetMapping()
    public List<FormaPagamentoDTO> listar(@PathVariable Long idRestaurante) {
        var restaurante = restauranteService.buscar(idRestaurante);
        return assembler.convertListToModel(restaurante.getFormasPagamento());
    }

    @PutMapping("/{idFormaPagamento}")
    public void adicionar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
        try {
            restauranteService.adicionarFormaPagamento(idRestaurante, idFormaPagamento);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @DeleteMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento) {
        try {
            restauranteService.removerFormaPagamento(idRestaurante, idFormaPagamento);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }
}
