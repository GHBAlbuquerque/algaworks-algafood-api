package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.v1.model.output.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.api.v1.utils.ResourceUriHelper;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1/formas-pagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoAssembler assembler;

    @CheckSecurity.FormasPagamento.PodeConsultar
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar() {
        var formasPagamento = formaPagamentoRepository.findAll();
        var formasPagamentoModel = assembler.toCollectionModel(formasPagamento);
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formasPagamentoModel);
    }

    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable long id) {
        var formaPagamento = formaPagamentoService.buscar(id);
        var formasPagamentoModel = assembler.toModel(formaPagamento);

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formasPagamentoModel);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput) {
        var formaPagamento = assembler.toEntity(formaPagamentoInput);
        formaPagamento = formaPagamentoService.salvar(formaPagamento);
        var model = assembler.toModel(formaPagamento);

        ResourceUriHelper.addUriInResponseHeader(model.getId());
        return model;
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> atualizar(@PathVariable long id, @RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput) {
        var formaPagamentoExistente = formaPagamentoService.buscar(id);
        assembler.copyToInstance(formaPagamentoInput, formaPagamentoExistente);

        var formaPagamento = formaPagamentoService.salvar(formaPagamentoExistente);
        return ResponseEntity.ok(assembler.toModel(formaPagamento));
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        formaPagamentoService.remover(id);
        return ResponseEntity.noContent().build();
    }

}
