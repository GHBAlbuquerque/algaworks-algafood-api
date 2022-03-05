package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoPedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener
    //@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT) --> posso especificar uma fase
    public void aoConfirmarPedido(PedidoCanceladoEvent event) {
        var pedido = event.getPedido();

        var mensagem = EnvioEmailService.
                Mensagem.builder()
                .assunto((String.format("%s - Pedido cancelado", pedido.getRestaurante().getNome())))
                .corpo("pedido-cancelado.html")
                .destinatario(pedido.getCliente().getEmail())
                .variavel("pedido", pedido)
                .build();

        envioEmailService.enviar(mensagem);

    }
}
