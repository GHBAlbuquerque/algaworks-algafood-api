package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoAssembler;
import com.algaworks.algafood.api.model.output.PermissaoDTO;
import com.algaworks.algafood.api.openapi.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.GrupoService;
import com.algaworks.algafood.domain.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("grupos/{idGrupo}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private PermissaoAssembler assembler;

    @GetMapping()
    public List<PermissaoDTO> listar(@PathVariable Long idGrupo) {
        var grupo = grupoService.buscar(idGrupo);
        return assembler.toCollectionModel(grupo.getPermissoes());
    }

    @PutMapping("/{idPermissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionar(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
        try {
            grupoService.adicionarPermissao(idGrupo, idPermissao);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @DeleteMapping("/{idPermissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
        try {
            grupoService.removerPermissao(idGrupo, idPermissao);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }
}
