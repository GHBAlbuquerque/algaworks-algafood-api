package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.input.ProdutoInputDTO;
import com.algaworks.algafood.api.v1.model.input.update.ProdutoUpdateDTO;
import com.algaworks.algafood.api.v1.model.output.ProdutoDTO;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    public CollectionModel<ProdutoDTO> listar(@PathVariable Long idRestaurante,
                                              @RequestParam(required = false) Boolean incluirInativos);

    public ProdutoDTO buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto);

    public ProdutoDTO adicionar(@PathVariable Long idRestaurante, @RequestBody @Valid ProdutoInputDTO produtoInputDTO);

    public void atualizar(@PathVariable Long idRestaurante, @PathVariable Long idProduto, @RequestBody @Valid ProdutoUpdateDTO produtoInputDTO);

    public void remover(@PathVariable Long idRestaurante, @PathVariable Long idProduto);
}


