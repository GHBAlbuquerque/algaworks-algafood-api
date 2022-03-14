package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.output.UsuarioDTO;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioControllerOpenApi {

    public CollectionModel<UsuarioDTO> listar(@PathVariable Long idRestaurante);

    public ResponseEntity<Void> adicionar(@PathVariable Long idRestaurante, @PathVariable Long idUsuario);

    public ResponseEntity<Void> remover(@PathVariable Long idRestaurante, @PathVariable Long idUsuario);
}
