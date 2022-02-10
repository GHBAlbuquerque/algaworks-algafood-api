package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.CidadeInputDTO;
import com.algaworks.algafood.api.model.saida.CidadeDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public Cidade convertToEntity(CidadeInputDTO cidade) {
        try {
            return modelMapper.map(cidade, Cidade.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }


    public void copyToInstance(CidadeInputDTO cidadeInputDTO, Cidade cidade) {
        try {
            cidade.setEstado(new Estado());
            modelMapper.map(cidadeInputDTO, cidade);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }

    public List<CidadeDTO> convertListToModel(Collection<Cidade> cidades) {
        return cidades.stream().map(this::convertToModel).collect(Collectors.toList());
    }
}
