package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.CozinhaEntradaDTO;
import com.algaworks.algafood.api.model.saida.CozinhaDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CozinhaAssembler {

    public CozinhaDTO convert(Cozinha cozinha) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.convertValue(cozinha, CozinhaDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Cozinha convert(CozinhaEntradaDTO cozinha) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.convertValue(cozinha, Cozinha.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.");
        }
    }
}
