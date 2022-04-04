package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PermissaoAssembler;
import com.algaworks.algafood.api.v1.model.output.PermissaoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.api.v1.utils.LinkGenerator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
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
@RequestMapping("/v1/grupos/{idGrupo}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private PermissaoAssembler assembler;

    @Autowired
    private LinkGenerator linkGenerator;

    @Autowired
    private AlgaSecurity algaSecurity;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping()
    public CollectionModel<PermissaoDTO> listar(@PathVariable Long idGrupo) {
        var grupo = grupoService.buscar(idGrupo);
        var models = assembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(linkGenerator.linkToGrupoPermissoes(idGrupo));

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            models.add(linkGenerator.linkToGrupoPermissaoAdicionar(idGrupo));

            models.forEach(model -> model.add(linkGenerator.linkToGrupoPermissaoRemover(idGrupo, model.getId())));
        }

        return models;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
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

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
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
