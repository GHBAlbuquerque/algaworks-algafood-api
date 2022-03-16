package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.EstadoAssembler;
import com.algaworks.algafood.api.v1.model.input.EstadoInputDTO;
import com.algaworks.algafood.api.v1.model.output.EstadoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.api.v1.utils.ResourceUriHelper;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/estados")
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoAssembler assembler;

    @GetMapping
    public CollectionModel<EstadoDTO> listar() {
        var estados = estadoRepository.findAll();
        return assembler.toCollectionModel(estados);
    }


    @GetMapping("/{id}")
    public EstadoDTO buscar(@PathVariable long id) {
        var estado = estadoService.buscar(id);
        return assembler.toModel(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(@RequestBody @Valid EstadoInputDTO estadoInput) {
        var estado = assembler.toEntity(estadoInput);
        var estadoSalvo = estadoService.salvar(estado);
        var model = assembler.toModel(estadoSalvo);

        ResourceUriHelper.addUriInResponseHeader(model.getId());
        return model;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> atualizar(@PathVariable long id, @RequestBody @Valid EstadoInputDTO estadoInput) {
        var estadoExistente = estadoService.buscar(id);
        assembler.copyToInstance(estadoInput, estadoExistente);

        var estado = estadoService.salvar(estadoExistente);
        return ResponseEntity.ok(assembler.toModel(estado));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        estadoService.remover(id);
        return ResponseEntity.noContent().build();
    }


}
