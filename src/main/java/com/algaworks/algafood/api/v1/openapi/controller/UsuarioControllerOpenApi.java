package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.v1.model.input.update.SenhaUpdateDTO;
import com.algaworks.algafood.api.v1.model.input.update.UsuarioUpdateDTO;
import com.algaworks.algafood.api.v1.model.output.UsuarioDTO;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    public CollectionModel<UsuarioDTO> listar(Pageable pageable);

    public UsuarioDTO buscar(@PathVariable long id);

    public UsuarioDTO salvar(@RequestBody @Valid UsuarioInputDTO usuarioInput);

    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable long id,
                                                @RequestBody @Valid UsuarioUpdateDTO usuarioInput);

    public ResponseEntity<?> deletar(@PathVariable long id);

    public ResponseEntity<UsuarioDTO> alterarSenha(@PathVariable long id, @RequestBody @Valid SenhaUpdateDTO senha);

}
