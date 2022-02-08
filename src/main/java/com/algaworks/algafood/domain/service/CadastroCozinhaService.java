package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.entrada.CozinhaEntradaDTO;
import com.algaworks.algafood.api.model.saida.CozinhaDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.entitynotfound.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroCozinhaService {

    private static final String MSG_COZINHA_EM_USO = "Cozinha de id %d não pode ser removida, pois está em uso!";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha buscar(long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        // qualquer regra de negócio virá aqui
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void remover(long id) {
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush();

        } catch (DataIntegrityViolationException e) {
            // tradução da exceção
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, id));

        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException(id);
        }
    }

}
