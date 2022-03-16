package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.GrupoAssembler;
import com.algaworks.algafood.api.v1.model.input.GrupoInputDTO;
import com.algaworks.algafood.api.v1.model.output.GrupoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.api.v1.utils.ResourceUriHelper;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/grupos")
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoAssembler assembler;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public CollectionModel<GrupoDTO> listar() {
        var grupos = grupoRepository.findAll();
        return assembler.toCollectionModel(grupos);
    }

    @GetMapping("/{id}")
    public GrupoDTO buscar(@PathVariable long id) {
        var grupo = grupoService.buscar(id);
        return assembler.toModel(grupo);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO salvar(@RequestBody @Valid GrupoInputDTO grupoInput) {
        var grupo = assembler.toEntity(grupoInput);
        grupo = grupoService.salvar(grupo);
        var model = assembler.toModel(grupo);

        ResourceUriHelper.addUriInResponseHeader(model.getId());
        return model;
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoDTO> atualizar(@PathVariable long id, @RequestBody @Valid GrupoInputDTO grupoInput) {
        var grupoExistente = grupoService.buscar(id);
        assembler.copyToInstance(grupoInput, grupoExistente);

        var grupo = grupoService.salvar(grupoExistente);
        return ResponseEntity.ok(assembler.toModel(grupo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        grupoService.remover(id);
        return ResponseEntity.noContent().build();
    }

}
