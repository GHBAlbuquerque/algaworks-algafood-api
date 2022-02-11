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
    public void confirmar(Long id){
        var pedido = pedidoService.buscar(id);
        pedido.confirmar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(Long id){
        var pedido = pedidoService.buscar(id);
        pedido.cancelar();
        pedidoRepository.save(pedido);

    }

    @Transactional
    public void entregar(Long id){
        var pedido = pedidoService.buscar(id);
        pedido.entregar();
        pedidoRepository.save(pedido);
    }
}
