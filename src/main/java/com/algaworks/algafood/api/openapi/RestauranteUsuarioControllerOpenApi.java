package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.output.UsuarioDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioControllerOpenApi {

    public List<UsuarioDTO> listar(@PathVariable Long idRestaurante);

    public void adicionar(@PathVariable Long idRestaurante, @PathVariable Long idUsuario);

    public void remover(@PathVariable Long idRestaurante, @PathVariable Long idUsuario);
}
