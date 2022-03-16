package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.input.CozinhaInputDTO;
import com.algaworks.algafood.api.v1.model.output.CozinhaDTO;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    public CollectionModel<CozinhaDTO> listar();

    public List<CozinhaDTO> listarXML();

    public CozinhaDTO buscar(@PathVariable long id);

    public ResponseEntity<List<CozinhaDTO>> buscarPorNome(@PathParam(value = "nome") String nome);

    public CozinhaDTO salvar(@RequestBody @Valid CozinhaInputDTO cozinhaInput);

    public ResponseEntity<CozinhaDTO> atualizar(@PathVariable long id, @RequestBody @Valid CozinhaInputDTO cozinhaInput);

    public ResponseEntity<?> deletar(@PathVariable long id);
}
