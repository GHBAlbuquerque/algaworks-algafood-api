package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.input.PermissaoInputDTO;
import com.algaworks.algafood.api.model.output.PermissaoDTO;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Permissão")
public interface PermissaoControllerOpenApi {

    public CollectionModel<PermissaoDTO> listar();

    public PermissaoDTO buscar(@PathVariable long id);

    public PermissaoDTO salvar(@RequestBody @Valid PermissaoInputDTO permissaoInput);

    public ResponseEntity<PermissaoDTO> atualizar(@PathVariable long id,
                                                  @RequestBody @Valid PermissaoInputDTO permissaoInput);

    public ResponseEntity<?> deletar(@PathVariable long id);

}
