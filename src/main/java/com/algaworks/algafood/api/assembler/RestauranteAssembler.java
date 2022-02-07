package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.RestauranteEntradaDTO;
import com.algaworks.algafood.api.model.saida.RestauranteDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class RestauranteAssembler {

    public RestauranteDTO convertToModel(Restaurante restaurante) {
        try {
            var objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            return objectMapper.convertValue(restaurante, RestauranteDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.", ex.getCause());
        }
    }

    public Restaurante convertToEntity(RestauranteEntradaDTO restaurante) {
        try {
            var objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            return objectMapper.convertValue(restaurante, Restaurante.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }
}
