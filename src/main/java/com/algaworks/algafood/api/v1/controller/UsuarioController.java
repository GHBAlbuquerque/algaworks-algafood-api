package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.v1.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.v1.model.input.update.SenhaUpdateDTO;
import com.algaworks.algafood.api.v1.model.input.update.UsuarioUpdateDTO;
import com.algaworks.algafood.api.v1.model.output.UsuarioDTO;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.api.v1.utils.ResourceUriHelper;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Usuario> pagedResourcesAssembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PagedModel<UsuarioDTO> listar(Pageable pageable) {
        var usuariosPage = usuarioRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(usuariosPage, assembler);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{id}")
    public UsuarioDTO buscar(@PathVariable long id) {
        var usuario = usuarioService.buscar(id);
        return assembler.toModel(usuario);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO salvar(@RequestBody @Valid UsuarioInputDTO usuarioInput) {
        var usuario = assembler.toEntity(usuarioInput);
        usuario = usuarioService.salvar(usuario);
        var model = assembler.toModel(usuario);

        ResourceUriHelper.addUriInResponseHeader(model.getId());
        return model;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable long id, @RequestBody @Valid UsuarioUpdateDTO usuarioInput) {
        var usuarioExistente = usuarioService.buscar(id);
        assembler.copyToInstance(usuarioInput, usuarioExistente);

        var usuario = usuarioService.salvar(usuarioExistente);
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping("/{id}/senha")
    public ResponseEntity<UsuarioDTO> alterarSenha(@PathVariable long id, @RequestBody @Valid SenhaUpdateDTO senha) {

        usuarioService.trocarSenha(id, senha);
        return ResponseEntity.noContent().build();
    }
}
