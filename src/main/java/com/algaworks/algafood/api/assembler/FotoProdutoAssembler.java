package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.model.input.FotoProdutoInputDTO;
import com.algaworks.algafood.api.model.output.FotoProdutoDTO;
import com.algaworks.algafood.api.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkGenerator linkGenerator;

    public FotoProdutoAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
    }

    public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
        try {
            var model = modelMapper.map(fotoProduto, FotoProdutoDTO.class);
            var restauranteId = fotoProduto.getRestauranteId();

            model.add(linkGenerator.linkToFotoProduto(model.getId(), restauranteId),
                    linkGenerator.linkToProduto(model.getId(), restauranteId).withRel("produto"));

            return model;

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
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    @Override
    public CollectionModel<FotoProdutoDTO> toCollectionModel(Iterable<? extends FotoProduto> entities) {
        return super.toCollectionModel(entities);
    }
}
