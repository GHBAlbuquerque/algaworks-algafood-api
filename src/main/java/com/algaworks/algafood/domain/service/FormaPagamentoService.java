package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.entitynotfound.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de Pagamento de id %d não pode ser removida, pois está em uso!";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento buscar(long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        // qualquer regra de negócio virá aqui
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void remover(long id) {
        try {
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();

        } catch (DataIntegrityViolationException e) {
            // tradução da exceção
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));

        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(id);
        }
    }

}
