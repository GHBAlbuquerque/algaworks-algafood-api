package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.input.FotoProdutoInputDTO;
import com.algaworks.algafood.api.model.output.FotoProdutoDTO;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    public FotoProdutoDTO atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
                                        @Valid FotoProdutoInputDTO fotoProdutoInputDTO) throws IOException;

    public FotoProdutoDTO buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto);

    public void deletar(@PathVariable Long idRestaurante, @PathVariable Long idProduto);

    public ResponseEntity<?> recuperar(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
                                       @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException;
}
