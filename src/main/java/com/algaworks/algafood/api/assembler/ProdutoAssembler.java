package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.ProdutoEntradaDTO;
import com.algaworks.algafood.api.model.entrada.ProdutoUpdateEntradaDTO;
import com.algaworks.algafood.api.model.saida.ProdutoDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO convertToModel(Produto produto) {
        try {
            return modelMapper.map(produto, ProdutoDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Produto convertToEntity(ProdutoEntradaDTO produto) {
        try {
            return modelMapper.map(produto, Produto.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.");
        }
    }


    public void copyToInstance(ProdutoEntradaDTO produtoEntrada, Produto produto) {
        try {
            modelMapper.map(produtoEntrada, produto);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }

    public void copyToInstance(ProdutoUpdateEntradaDTO produtoEntrada, Produto produto) {
        try {
            modelMapper.map(produtoEntrada, produto);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }

    public List<ProdutoDTO> convertListToModel(List<Produto> produtos) {
        return produtos.stream().map(this::convertToModel).collect(Collectors.toList());
    }
}
