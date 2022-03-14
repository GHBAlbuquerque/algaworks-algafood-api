package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.output.FormaPagamentoDTO;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long idRestaurante);

    public ResponseEntity<Void> adicionar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento);

    public ResponseEntity<Void>  remover(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento);
}
