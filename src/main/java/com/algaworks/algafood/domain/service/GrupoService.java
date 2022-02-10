package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.entitynotfound.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GrupoService {

    private static final String MSG_GRUPO_EM_USO = "Grupo de id %d não pode ser removido, pois está em uso!";

    @Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired
    private PermissaoService permissaoService;

    public Grupo buscar(long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void remover(long id) {
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();

        } catch (DataIntegrityViolationException e) {
            // tradução da exceção
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, id));

        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id);
        }
    }

    @Transactional
    public void removerPermissao(Long idGrupo, Long idPermissao) {
        var grupo = buscar(idGrupo);
        var permissao = permissaoService.buscar(idPermissao);

        if (!grupo.getPermissoes().contains(permissao))
            throw new NegocioException("Grupo não possui a permissão solicitada para remoção.");

        grupo.getPermissoes().remove(permissao);
        grupoRepository.save(grupo);
    }

    @Transactional
    public void adicionarPermissao(Long idGrupo, Long idPermissao) {
        var grupo = buscar(idGrupo);
        var permissao = permissaoService.buscar(idPermissao);

        if (grupo.getPermissoes().contains(permissao))
            throw new NegocioException("Grupo já possui a permissão solicitada para adição.");

        grupo.getPermissoes().add(permissao);
        grupoRepository.save(grupo);
    }

}
