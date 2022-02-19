package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.infrastructure.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StatusPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private EnvioEmailService envioEmailService;


    @Transactional
    public void confirmar(String codigo){
        var pedido = pedidoService.buscar(codigo);
        pedido.confirmar();

        var mensagem = EnvioEmailService.
                Mensagem.builder()
                .assunto((String.format("%s - Pedido confirmado", pedido.getRestaurante().getNome())))
                .corpo(String.format("O pedido de código<strong> %s </strong>foi confirmado.", pedido.getId()))
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(String codigo){
        var pedido = pedidoService.buscar(codigo);
        pedido.cancelar();
        pedidoRepository.save(pedido);

    }

    @Transactional
    public void entregar(String codigo){
        var pedido = pedidoService.buscar(codigo);
        pedido.entregar();
        pedidoRepository.save(pedido);
    }
}
