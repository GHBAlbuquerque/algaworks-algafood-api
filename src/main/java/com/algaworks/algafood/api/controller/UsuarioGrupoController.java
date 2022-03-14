package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoAssembler;
import com.algaworks.algafood.api.model.output.GrupoDTO;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.api.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.GrupoService;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuarios/{idUsuario}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoAssembler assembler;

    @Autowired
    private LinkGenerator linkGenerator;

    @GetMapping()
    public CollectionModel<GrupoDTO> listar(@PathVariable Long idUsuario) {
        var usuario = usuarioService.buscar(idUsuario);
        return assembler.toCollectionModel(usuario.getGrupos())
                .removeLinks()
                .add(linkGenerator
                                .linkToUsuarioGrupos(idUsuario)
                                .withSelfRel(),
                        linkGenerator
                                .linkToUsuarioGruposAssociar(idUsuario));
    }

    @PutMapping("/{idGrupo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
        try {
            usuarioService.associarGrupo(idUsuario, idGrupo);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @DeleteMapping("/{idGrupo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
        try {
            usuarioService.desassociarGrupo(idUsuario, idGrupo);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

}
