package com.algaworks.algafood.domain.service;


import com.algaworks.algafood.domain.enums.StatusPedidoEnum;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
public class StatusPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;


    @Transactional
    public Pedido confirmar(Long id){
        var pedido = pedidoService.buscar(id);
        var status = pedido.getStatus();

        if(!status.equals(StatusPedidoEnum.CRIADO)) {
            throw new NegocioException(String.format("O pedido %s não pode ser alterado de %s para %s.", id,
                    status, StatusPedidoEnum.CONFIRMADO));
        }

        pedido.setStatus(StatusPedidoEnum.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
        pedidoRepository.save(pedido);

        return pedido;
    }

    @Transactional
    public void cancelar(Long id){
        var pedido = pedidoService.buscar(id);
        var status = pedido.getStatus();

        if(status.equals(StatusPedidoEnum.ENTREGUE)) {
            throw new NegocioException(String.format("O pedido %s não pode ser cancelado pois já foi entregue.", id));
        }

        pedido.setStatus(StatusPedidoEnum.CANCELADO);
        pedido.setDataCancelamento(OffsetDateTime.now());
        pedidoRepository.save(pedido);

    }

    @Transactional
    public Pedido entregar(Long id){
        var pedido = pedidoService.buscar(id);
        var status = pedido.getStatus();

        if(!status.equals(StatusPedidoEnum.CONFIRMADO)) {
            throw new NegocioException(String.format("O pedido %s não pode ser alterado de %s para %s.", id,
                    status, StatusPedidoEnum.ENTREGUE));
        }

        pedido.setStatus(StatusPedidoEnum.ENTREGUE);
        pedido.setDataEntrega(OffsetDateTime.now());
        pedidoRepository.save(pedido);
        return pedido;
    }
}
