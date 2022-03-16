package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.output.GrupoDTO;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    public CollectionModel<GrupoDTO> listar(@PathVariable Long idUsuario);

    public ResponseEntity<Void> associar(@PathVariable Long idUsuario, @PathVariable Long idGrupo);

    public ResponseEntity<Void> desassociar(@PathVariable Long idUsuario, @PathVariable Long idGrupo);

}
