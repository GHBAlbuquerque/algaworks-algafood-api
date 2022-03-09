package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.api.model.output.PedidoDTO;
import com.algaworks.algafood.api.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkGenerator linkGenerator;

    public PedidoAssembler() {
        super(PedidoController.class, PedidoDTO.class);
    }

    public PedidoDTO toModel(Pedido pedido) {
        try {
            var model = modelMapper.map(pedido, PedidoDTO.class);
            
            model.add(linkGenerator.linkToPedidosPesquisar());

            model.add(linkTo(
                    methodOn(PedidoController.class)
                            .buscar(model.getCodigo()))
                    .withSelfRel());

            model.add(linkTo(
                    methodOn(PedidoController.class)
                            .listar())
                    .withRel(IanaLinkRelations.COLLECTION));

            var cliente = model.getCliente();

            cliente.add(linkTo(
                    methodOn(UsuarioController.class)
                            .buscar(cliente.getId()))
                    .withSelfRel());

            cliente.add(linkTo(
                    methodOn(UsuarioController.class)
                            .listar(null))
                    .withRel(IanaLinkRelations.COLLECTION));

            var restaurante = model.getRestaurante();

            restaurante.add(linkTo(
                    methodOn(RestauranteController.class)
                            .buscar(restaurante.getId(), null))
                    .withSelfRel());

            restaurante.add(linkTo(
                    methodOn(RestauranteController.class)
                            .listar())
                    .withRel(IanaLinkRelations.COLLECTION));

            var formaPagamento = model.getFormaPagamento();

            formaPagamento.add(linkTo(
                    methodOn(FormaPagamentoController.class)
                            .buscar(formaPagamento.getId()))
                    .withSelfRel());

            formaPagamento.add(linkTo(
                    methodOn(FormaPagamentoController.class)
                            .listar())
                    .withRel(IanaLinkRelations.COLLECTION));

            return model;
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Pedido toEntity(PedidoInputDTO pedido) {
        try {
            return modelMapper.map(pedido, Pedido.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }

    public void copyToInstance(PedidoInputDTO pedidoInput, Pedido pedido) {
        try {
            modelMapper.map(pedidoInput, pedido);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    @Override
    public CollectionModel<PedidoDTO> toCollectionModel(Iterable<? extends Pedido> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(PedidoController.class)
                        .withSelfRel());
    }
}
