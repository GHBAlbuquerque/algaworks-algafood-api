package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.EstadoInputDTO;
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

    public Estado convertToEntity(EstadoInputDTO estado) {
        try {
            return modelMapper.map(estado, Estado.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }


    public void copyToInstance(EstadoInputDTO estadoInput, Estado estado) {
        try {
            modelMapper.map(estadoInput, estado);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }
}
