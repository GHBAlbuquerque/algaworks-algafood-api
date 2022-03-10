package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.model.input.FotoProdutoInputDTO;
import com.algaworks.algafood.api.model.output.FotoProdutoDTO;
import com.algaworks.algafood.api.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoProdutoAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkGenerator linkGenerator;

    public FotoProdutoAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
    }

    public FotoProdutoDTO toModel(FotoProduto FotoProduto) {
        try {
            return modelMapper.map(FotoProduto, FotoProdutoDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public FotoProduto toEntity(FotoProdutoInputDTO FotoProduto) {
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

    public List<FotoProdutoDTO> toCollectionModel(Collection<FotoProduto> FotoProdutos) {
        return FotoProdutos.stream().map(this::toModel).collect(Collectors.toList());
    }
}
