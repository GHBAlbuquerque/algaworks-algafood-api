package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.model.output.UsuarioDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioControllerOpenApi;
import com.algaworks.algafood.api.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/responsaveis")
public class RestauranteUsuarioController implements RestauranteUsuarioControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAssembler assembler;

    @Autowired
    private LinkGenerator linkGenerator;

    @GetMapping()
    public CollectionModel<UsuarioDTO> listar(@PathVariable Long idRestaurante) {
        var restaurante = restauranteService.buscar(idRestaurante);
        var responsaveis = assembler.toCollectionModel(restaurante.getResponsaveis());

        for (var responsavel : responsaveis) {
            responsavel.add(linkGenerator.linkToResponsavelRestauranteRemover(idRestaurante,
                    responsavel.getId()));
        }

        return responsaveis.removeLinks() //link do assembler nao condiz com a url correta e adiciono manualmente
                .add(linkGenerator.linkToResponsaveisRestaurante(idRestaurante)
                                .withSelfRel(),
                        linkGenerator.linkToResponsavelRestauranteAdicionar(idRestaurante));
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Void> adicionar(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
        try {
            restauranteService.adicionarResponsavel(idRestaurante, idUsuario);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @DeleteMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
        try {
            restauranteService.removerResponsavel(idRestaurante, idUsuario);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }
}
