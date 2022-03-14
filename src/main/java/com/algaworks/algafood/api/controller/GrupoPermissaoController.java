package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoAssembler;
import com.algaworks.algafood.api.model.output.PermissaoDTO;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.api.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.GrupoService;
import com.algaworks.algafood.domain.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("grupos/{idGrupo}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private PermissaoAssembler assembler;

    @Autowired
    private LinkGenerator linkGenerator;

    @GetMapping()
    public CollectionModel<PermissaoDTO> listar(@PathVariable Long idGrupo) {
        var grupo = grupoService.buscar(idGrupo);
        var models= assembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(linkGenerator.linkToGrupoPermissoes(idGrupo),
                        linkGenerator.linkToGrupoPermissaoAdicionar(idGrupo));

        models.forEach(model -> model.add(linkGenerator.linkToGrupoPermissaoRemover(idGrupo, model.getId())));

        return models;
    }

    @PutMapping("/{idPermissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> adicionar(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
        try {
            grupoService.adicionarPermissao(idGrupo, idPermissao);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @DeleteMapping("/{idPermissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
        try {
            grupoService.removerPermissao(idGrupo, idPermissao);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }
}
