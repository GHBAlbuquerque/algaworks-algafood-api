package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.entitynotfound.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PedidoService {

    private static final String MSG_PEDIDO_EM_USO = "Pedido de id %d não pode ser removido, pois está em uso!";

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private UsuarioService usuarioService;

    public Pedido buscar(String codigo) {
        return pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigo));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.definirFrete();
        pedido.calcularValorTotal();
        pedido.atribuirPedidoAosItens();

        return pedidoRepository.save(pedido);
    }

    public void validarPedido(Pedido pedido) {
        //valida existência das entidades referenciadas
        var restaurante = restauranteService.buscar(pedido.getRestaurante().getId());
        var formaPagamento = formaPagamentoService.buscar(pedido.getFormaPagamento().getId());
        var usuario = usuarioService.buscar(pedido.getCliente().getId());
        var cidade = cidadeService.buscar(pedido.getEnderecoEntrega().getCidade().getId());

        definirEntidadesAssociadas(pedido, restaurante, formaPagamento, usuario, cidade);

        //valida forma de pagamento aceita
        if (!restaurante.getFormasPagamento()
                .contains(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento %s não é aceita por este restaurante.", formaPagamento.getDescricao()));
        }

    }

    private void definirEntidadesAssociadas(Pedido pedido, Restaurante restaurante, FormaPagamento formaPagamento,
                                            Usuario cliente, Cidade cidade) {
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setCliente(cliente);
        pedido.getEnderecoEntrega().setCidade(cidade);
    }

    private void validarItens(Pedido pedido) {
        // valida produto disponível no restaurante escolhido
        // seta produto no item e define totais

        var itens = pedido.getItens();

        for (var item : itens) {
            var idProduto = item.getProduto().getId();
            var produto = restauranteService.buscarProdutoPorRestaurante(pedido.getRestaurante().getId(), idProduto);

            item.setProduto(produto);
            item.definirPrecoTotal();
        }
    }


}
