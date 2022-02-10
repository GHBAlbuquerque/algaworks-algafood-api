package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.input.CidadeInputDTO;
import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        modelMapper.createTypeMap(RestauranteInputDTO.class, Restaurante.class)
                .addMappings(mapper -> mapper.skip(Restaurante::setId));

        modelMapper.createTypeMap(CidadeInputDTO.class, Cidade.class)
                .addMappings(mapper -> mapper.skip(Cidade::setId));

        return modelMapper;
    }
}
