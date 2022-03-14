package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoAssembler;
import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import com.algaworks.algafood.api.model.input.update.ProdutoUpdateDTO;
import com.algaworks.algafood.api.model.output.ProdutoDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.api.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoAssembler assembler;

    @Autowired
    private LinkGenerator linkGenerator;

    @GetMapping()
    public CollectionModel<ProdutoDTO> listar(@PathVariable Long idRestaurante,
                                              @RequestParam(required = false) Boolean incluirInativos) {
        var produtos = restauranteService.listarProdutosPorRestaurante(idRestaurante, incluirInativos);
        return assembler.toCollectionModel(produtos).add(linkGenerator.linkToProdutos(idRestaurante));
    }

    @GetMapping("/{idProduto}")
    public ProdutoDTO buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        var produto = restauranteService.buscarProdutoPorRestaurante(idRestaurante, idProduto);
        return assembler.toModel(produto);
    }

    @PostMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long idRestaurante, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        try {
            var produto = assembler.toEntity(produtoInputDTO);
            produto = restauranteService.adicionarProduto(idRestaurante, produto);
            return assembler.toModel(produto);
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

    @PutMapping("/{idProduto}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        restauranteService.ativar(idProduto, idRestaurante);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idProduto}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desativar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        restauranteService.desativar(idProduto, idRestaurante);
        return ResponseEntity.noContent().build();
    }
}
