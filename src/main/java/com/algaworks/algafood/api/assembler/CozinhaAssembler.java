package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.CozinhaEntradaDTO;
import com.algaworks.algafood.api.model.saida.CozinhaDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO convertToModel(Cozinha cozinha) {
        try {
            return modelMapper.map(cozinha, CozinhaDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Cozinha convertToEntity(CozinhaEntradaDTO cozinha) {
        try {
            return modelMapper.map(cozinha, Cozinha.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.");
        }
    }


    public void copyToInstance(CozinhaEntradaDTO cozinhaEntrada, Cozinha cozinha) {
        try {
            modelMapper.map(cozinhaEntrada, cozinha);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }
}
