package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.CidadeEntradaDTO;
import com.algaworks.algafood.api.model.saida.CidadeDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CidadeAssembler {

    public CidadeDTO convertToModel(Cidade cidade) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.convertValue(cidade, CidadeDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.", ex.getCause());
        }
    }

    public Cidade convertToEntity(CidadeEntradaDTO cidade) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.convertValue(cidade, Cidade.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }
}
