package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.output.PermissaoDTO;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    public CollectionModel<PermissaoDTO> listar(@PathVariable Long idGrupo);

    public ResponseEntity<Void> adicionar(@PathVariable Long idGrupo, @PathVariable Long idPermissao);

    public ResponseEntity<Void> remover(@PathVariable Long idGrupo, @PathVariable Long idPermissao);

}
