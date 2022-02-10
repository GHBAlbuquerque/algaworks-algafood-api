package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.entitynotfound.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PedidoService {

    private static final String MSG_PEDIDO_EM_USO = "Pedido de id %d não pode ser removido, pois está em uso!";

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscar(long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        // qualquer regra de negócio virá aqui
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void remover(long id) {
        try {
            pedidoRepository.deleteById(id);
            pedidoRepository.flush();

        } catch (DataIntegrityViolationException e) {
            // tradução da exceção
            throw new EntidadeEmUsoException(
                    String.format(MSG_PEDIDO_EM_USO, id));

        } catch (EmptyResultDataAccessException e) {
            throw new PedidoNaoEncontradoException(id);
        }
    }

}
