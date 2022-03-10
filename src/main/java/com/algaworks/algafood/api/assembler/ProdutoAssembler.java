package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import com.algaworks.algafood.api.model.input.update.ProdutoUpdateDTO;
import com.algaworks.algafood.api.model.output.ProdutoDTO;
import com.algaworks.algafood.api.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkGenerator linkGenerator;

    public ProdutoAssembler() {
        super(RestauranteProdutoController.class, ProdutoDTO.class);
    }

    public ProdutoDTO toModel(Produto produto) {
        try {
            return modelMapper.map(produto, ProdutoDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Produto toEntity(ProdutoInputDTO produto) {
        try {
            return modelMapper.map(produto, Produto.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }


    public void copyToInstance(ProdutoInputDTO produtoInput, Produto produto) {
        try {
            modelMapper.map(produtoInput, produto);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    public void copyToInstance(ProdutoUpdateDTO produtoInput, Produto produto) {
        try {
            modelMapper.map(produtoInput, produto);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    public List<ProdutoDTO> toCollectionModel(Collection<Produto> produtos) {
        return produtos.stream().map(this::toModel).collect(Collectors.toList());
    }
}
