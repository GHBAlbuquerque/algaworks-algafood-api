package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.api.model.output.PedidoDTO;
import com.algaworks.algafood.api.model.output.PedidoSimpleDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO convertToModel(Pedido pedido) {
        try {
            return modelMapper.map(pedido, PedidoDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public PedidoSimpleDTO convertToSimpleModel(Pedido pedido) {
        try {
            return modelMapper.map(pedido, PedidoSimpleDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Pedido convertToEntity(PedidoInputDTO pedido) {
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

    public List<PedidoDTO> convertListToModel(Collection<Pedido> pedidos) {
        return pedidos.stream().map(this::convertToModel).collect(Collectors.toList());
    }

    public List<PedidoSimpleDTO> convertListToSimpleModel(Collection<Pedido> pedidos) {
        return pedidos.stream().map(this::convertToSimpleModel).collect(Collectors.toList());
    }
}