package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.CidadeEntradaDTO;
import com.algaworks.algafood.api.model.saida.CidadeDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTO convertToModel(Cidade cidade) {
        try {
            return modelMapper.map(cidade, CidadeDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.", ex.getCause());
        }
    }

    public Cidade convertToEntity(CidadeEntradaDTO cidade) {
        try {
            return modelMapper.map(cidade, Cidade.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }


    public void copyToInstance(CidadeEntradaDTO cidadeEntrada, Cidade cidade) {
        try {
            cidade.setEstado(new Estado());
            modelMapper.map(cidadeEntrada, cidade);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }
}
