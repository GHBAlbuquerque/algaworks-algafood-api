package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.model.output.FormaPagamentoDTO;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

    public ResponseEntity<List<FormaPagamentoDTO>> listar();

    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable long id);

    public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput);

    public ResponseEntity<FormaPagamentoDTO> atualizar(@PathVariable long id,
                                                       @RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput);

    public ResponseEntity<?> deletar(@PathVariable long id);

}