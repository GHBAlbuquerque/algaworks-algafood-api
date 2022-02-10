package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.api.model.saida.RestauranteDTO;
import com.algaworks.algafood.api.model.saida.RestauranteSingletonDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.apache.commons.lang3.ObjectUtils;
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

    public RestauranteSingletonDTO convertToSingletonModel(Restaurante restaurante) {
        try {
            return modelMapper.map(restaurante, RestauranteSingletonDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.", ex.getCause());
        }
    }

    public Restaurante convertToEntity(RestauranteInputDTO restaurante) {
        try {
            return modelMapper.map(restaurante, Restaurante.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }

    public void copyToInstance(RestauranteInputDTO restauranteInput, Restaurante restaurante) {
        try {
            if(ObjectUtils.isNotEmpty(restauranteInput.getCozinhaId())) {
                restaurante.setCozinha(new Cozinha());
            }

            if(ObjectUtils.isNotEmpty(restauranteInput.getCidade())) {
                restaurante.getEndereco().setCidade(new Cidade());
            }

            modelMapper.map(restauranteInput, restaurante);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }
}
