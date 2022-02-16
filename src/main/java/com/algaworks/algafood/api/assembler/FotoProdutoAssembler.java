package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.FotoProdutoInputDTO;
import com.algaworks.algafood.api.model.output.FotoProdutoDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoProdutoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO convertToModel(FotoProduto FotoProduto) {
        try {
            return modelMapper.map(FotoProduto, FotoProdutoDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public FotoProduto convertToEntity(FotoProdutoInputDTO FotoProduto) {
        try {
            return modelMapper.map(FotoProduto, FotoProduto.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }


    public void copyToInstance(FotoProdutoInputDTO FotoProdutoInput, FotoProduto FotoProduto) {
        try {
            modelMapper.map(FotoProdutoInput, FotoProduto);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }

    public List<FotoProdutoDTO> convertListToModel(Collection<FotoProduto> FotoProdutos) {
        return FotoProdutos.stream().map(this::convertToModel).collect(Collectors.toList());
    }
}
