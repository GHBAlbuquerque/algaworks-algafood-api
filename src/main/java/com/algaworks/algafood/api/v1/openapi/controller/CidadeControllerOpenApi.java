package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.input.CidadeInputDTO;
import com.algaworks.algafood.api.v1.model.output.CidadeDTO;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    public CollectionModel<CidadeDTO> listar();

    public CidadeDTO buscar(@PathVariable long id);

    public CidadeDTO adicionar(@RequestBody @Valid CidadeInputDTO cidadeInput);

    public ResponseEntity<CidadeDTO> atualizar(@PathVariable long id, @RequestBody @Valid CidadeInputDTO cidadeInput);

    public ResponseEntity<?> deletar(@PathVariable long id);
}
