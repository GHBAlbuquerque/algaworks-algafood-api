package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.entrada.CidadeEntradaDTO;
import com.algaworks.algafood.api.model.entrada.RestauranteEntradaDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(RestauranteEntradaDTO.class, Restaurante.class)
                .addMappings(mapper -> mapper.skip(Restaurante::setId));

        modelMapper.createTypeMap(CidadeEntradaDTO.class, Cidade.class)
                .addMappings(mapper -> mapper.skip(Cidade::setId));

        return modelMapper;
    }
}
