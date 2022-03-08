package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.model.output.UsuarioDTO;
import com.algaworks.algafood.api.openapi.RestauranteUsuarioControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/responsaveis")
public class RestauranteUsuarioController implements RestauranteUsuarioControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAssembler assembler;

    @GetMapping()
    public List<UsuarioDTO> listar(@PathVariable Long idRestaurante) {
        var restaurante = restauranteService.buscar(idRestaurante);
        return assembler.convertListToModel(restaurante.getResponsaveis());
    }

    @PutMapping("/{idUsuario}")
    public void adicionar(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
        try {
            restauranteService.adicionarResponsavel(idRestaurante, idUsuario);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @DeleteMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
        try {
            restauranteService.removerResponsavel(idRestaurante, idUsuario);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }
}
