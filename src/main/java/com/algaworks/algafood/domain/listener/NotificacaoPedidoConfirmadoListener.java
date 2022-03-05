package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoPedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener
    //@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT) --> posso especificar uma fase
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
