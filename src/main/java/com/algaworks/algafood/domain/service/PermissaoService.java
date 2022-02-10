package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.entitynotfound.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PermissaoService {

    private static final String MSG_PERMISSAO_EM_USO = "Permissao de id %d não pode ser removido, pois está em uso!";

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscar(long id) {
        return permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(id));
    }

    @Transactional
    public Permissao salvar(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    @Transactional
    public void remover(long id) {
        try {
            permissaoRepository.deleteById(id);
            permissaoRepository.flush();

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PERMISSAO_EM_USO, id));

        } catch (EmptyResultDataAccessException e) {
            throw new PermissaoNaoEncontradaException(id);
        }
    }

}
