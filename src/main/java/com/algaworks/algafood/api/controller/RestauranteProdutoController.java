package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoAssembler;
import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import com.algaworks.algafood.api.model.input.ProdutoUpdateDTO;
import com.algaworks.algafood.api.model.saida.ProdutoDTO;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoAssembler assembler;

    @GetMapping()
    public List<ProdutoDTO> listar(@PathVariable Long idRestaurante) {
        var produtos = restauranteService.buscarProdutosPorRestaurante(idRestaurante);
        return assembler.convertListToModel(produtos);
    }

    @GetMapping("/{idProduto}")
    public ProdutoDTO buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        var produto = restauranteService.buscarProdutoPorRestaurante(idRestaurante, idProduto);
        return assembler.convertToModel(produto);
    }

    @PostMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long idRestaurante, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        try {
            var produto = assembler.convertToEntity(produtoInputDTO);
            produto = restauranteService.adicionarProduto(idRestaurante, produto);
            return assembler.convertToModel(produto);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @PutMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Long idRestaurante, @PathVariable Long idProduto, @RequestBody @Valid ProdutoUpdateDTO produtoInputDTO) {
        try {
            var produtoExistente = restauranteService.buscarProdutoPorRestaurante(idRestaurante, idProduto);
            assembler.copyToInstance(produtoInputDTO, produtoExistente);
            restauranteService.atualizarProduto(idRestaurante, produtoExistente);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @DeleteMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        try {
            restauranteService.removerProduto(idRestaurante, idProduto);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }
}
