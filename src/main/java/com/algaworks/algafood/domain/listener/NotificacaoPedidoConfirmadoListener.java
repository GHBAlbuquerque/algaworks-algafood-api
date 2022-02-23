package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.infrastructure.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoPedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @EventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        var pedido = event.getPedido();

        var mensagem = EnvioEmailService.
                Mensagem.builder()
                .assunto((String.format("%s - Pedido confirmado", pedido.getRestaurante().getNome())))
                .corpo("pedido-confirmado.html")
                .destinatario(pedido.getCliente().getEmail()) //posso usar isso quantas vezes quiser
                .variavel("pedido", pedido)
                .build();

        envioEmailService.enviar(mensagem);

    }
}
