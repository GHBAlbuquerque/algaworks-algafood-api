package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.v1.model.output.FormaPagamentoDTO;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

    public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar();

    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable long id);

    public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput);

    public ResponseEntity<FormaPagamentoDTO> atualizar(@PathVariable long id,
                                                       @RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput);

    public ResponseEntity<?> deletar(@PathVariable long id);

}
