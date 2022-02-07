package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.EstadoEntradaDTO;
import com.algaworks.algafood.api.model.saida.EstadoDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Estado;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class EstadoAssembler {

    public EstadoDTO convert(Estado estado) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.convertValue(estado, EstadoDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Estado convert(EstadoEntradaDTO estado) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.convertValue(estado, Estado.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.");
        }
    }
}
