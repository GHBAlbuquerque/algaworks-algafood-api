package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.output.GrupoDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    public List<GrupoDTO> listar(@PathVariable Long idUsuario);

    public void associar(@PathVariable Long idUsuario, @PathVariable Long idGrupo);

    public void desassociar(@PathVariable Long idUsuario, @PathVariable Long idGrupo);

}
