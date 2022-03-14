package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.input.GrupoInputDTO;
import com.algaworks.algafood.api.model.output.GrupoDTO;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    public CollectionModel<GrupoDTO> listar();

    public GrupoDTO buscar(@PathVariable long id);

    public GrupoDTO salvar(@RequestBody @Valid GrupoInputDTO grupoInput);

    public ResponseEntity<GrupoDTO> atualizar(@PathVariable long id, @RequestBody @Valid GrupoInputDTO grupoInput);

    public ResponseEntity<?> deletar(@PathVariable long id);
}
