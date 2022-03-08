package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.output.PermissaoDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    public List<PermissaoDTO> listar(@PathVariable Long idGrupo);

    public void adicionar(@PathVariable Long idGrupo, @PathVariable Long idPermissao);

    public void remover(@PathVariable Long idGrupo, @PathVariable Long idPermissao);

}
