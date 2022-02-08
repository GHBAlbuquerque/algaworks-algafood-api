package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.EstadoEntradaDTO;
import com.algaworks.algafood.api.model.saida.EstadoDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTO convertToModel(Estado estado) {
        try {
            return modelMapper.map(estado, EstadoDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Estado convertToEntity(EstadoEntradaDTO estado) {
        try {
            return modelMapper.map(estado, Estado.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.");
        }
    }


    public void copyToInstance(EstadoEntradaDTO estadoEntrada, Estado estado) {
        try {
            modelMapper.map(estadoEntrada, estado);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }
}
