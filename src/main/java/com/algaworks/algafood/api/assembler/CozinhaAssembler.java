package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.CozinhaInputDTO;
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

    public Cozinha convertToEntity(CozinhaInputDTO cozinha) {
        try {
            return modelMapper.map(cozinha, Cozinha.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }


    public void copyToInstance(CozinhaInputDTO cozinhaInput, Cozinha cozinha) {
        try {
            modelMapper.map(cozinhaInput, cozinha);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }
}
