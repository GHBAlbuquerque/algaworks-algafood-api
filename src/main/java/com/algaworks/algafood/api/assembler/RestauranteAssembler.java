package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.RestauranteEntradaDTO;
import com.algaworks.algafood.api.model.saida.RestauranteDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDTO convertToModel(Restaurante restaurante) {
        try {
            return modelMapper.map(restaurante, RestauranteDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.", ex.getCause());
        }
    }

    public Restaurante convertToEntity(RestauranteEntradaDTO restaurante) {
        try {
            return modelMapper.map(restaurante, Restaurante.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }

    public void copyToInstance(RestauranteEntradaDTO restauranteEntrada, Restaurante restaurante) {
        try {
            restaurante.setCozinha(new Cozinha());
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.map(restauranteEntrada, restaurante);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }
}
