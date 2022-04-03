package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.CidadeAssembler;
import com.algaworks.algafood.api.v1.model.input.CidadeInputDTO;
import com.algaworks.algafood.api.v1.model.output.CidadeDTO;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.api.v1.utils.ResourceUriHelper;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeAssembler assembler;

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping
    public CollectionModel<CidadeDTO> listar() {
        var cidades = cidadeRepository.findAll();
        return assembler.toCollectionModel(cidades);
    }

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping("/{id}")
    public CidadeDTO buscar(@PathVariable long id) {
        var cidade = cidadeService.buscar(id);
        return assembler.toModel(cidade);
    }

    @CheckSecurity.Cidades.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody @Valid CidadeInputDTO cidadeInput) {
        try {
            var cidade = assembler.toEntity(cidadeInput);
            var cidadeSalva = cidadeService.salvar(cidade);
            var model = assembler.toModel(cidadeSalva);

            ResourceUriHelper.addUriInResponseHeader(model.getId());

            return model;
        } catch (EstadoNaoEncontradoException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }

    }

    @CheckSecurity.Cidades.PodeEditar
    @PutMapping("/{id}")
    public ResponseEntity<CidadeDTO> atualizar(@PathVariable long id, @RequestBody @Valid CidadeInputDTO cidadeInput) {
        var cidadeExistente = cidadeService.buscar(id);
        assembler.copyToInstance(cidadeInput, cidadeExistente);

        try {
            var cidade = cidadeService.salvar(cidadeExistente);
            return ResponseEntity.ok(assembler.toModel(cidadeExistente));
        } catch (EstadoNaoEncontradoException ex) {
            var idEstado = cidadeInput.getEstadoId();
            throw new EntidadeReferenciadaInexistenteException(Estado.class, idEstado);
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        cidadeService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
