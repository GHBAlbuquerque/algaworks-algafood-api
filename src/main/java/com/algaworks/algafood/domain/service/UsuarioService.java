package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.model.input.update.SenhaUpdateDTO;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.entitynotfound.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class UsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuario de id %d não pode ser removido, pois está em uso!";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario buscar(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        entityManager.detach(usuario);

        var usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format("Já existe um usuário cadastrado com o email %s.", usuario.getEmail()));
        }

        if (usuario.isNovo()) {
            var senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void remover(long id) {
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();

        } catch (DataIntegrityViolationException e) {
            // tradução da exceção
            throw new EntidadeEmUsoException(
                    String.format(MSG_USUARIO_EM_USO, id));

        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(id);
        }
    }

    @Transactional
    public void trocarSenha(long id, SenhaUpdateDTO senha) {
        var usuario = buscar(id);
        var senhaAtual = usuario.getSenha();

        if (!passwordEncoder.matches(senha.getSenhaAtual(), senhaAtual))
            throw new NegocioException("Senha atual não coincide com a senha do usuário.");

        var senhaCriptograda = passwordEncoder.encode(senha.getNovaSenha());
        usuario.setSenha(senha.getNovaSenha());
        usuarioRepository.save(usuario);
    }

    // MANIPULAÇÃO DE GRUPOS

    @Transactional
    public void associarGrupo(Long idUsuario, Long idGrupo) {
        var usuario = buscar(idUsuario);
        var grupo = grupoService.buscar(idGrupo);

        usuario.getGrupos().add(grupo);
        usuarioRepository.save(usuario);

    }

    @Transactional
    public void desassociarGrupo(Long idUsuario, Long idGrupo) {
        var usuario = buscar(idUsuario);
        var grupo = grupoService.buscar(idGrupo);

        usuario.getGrupos().remove(grupo);
        usuarioRepository.save(usuario);
    }

}
