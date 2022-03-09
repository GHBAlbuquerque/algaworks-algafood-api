package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.model.input.CozinhaInputDTO;
import com.algaworks.algafood.api.model.output.CozinhaDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CozinhaAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaAssembler() {
        super(CozinhaController.class, CozinhaDTO.class);
    }

    public CozinhaDTO toModel(Cozinha cozinha) {
        try {
            var model = modelMapper.map(cozinha, CozinhaDTO.class);
            
            model.add(linkTo(
                    methodOn(CozinhaController.class)
                            .buscar(model.getId()))
                    .withSelfRel());

            model.add(linkTo(
                    methodOn(CozinhaController.class)
                            .listar())
                    .withRel(IanaLinkRelations.COLLECTION));
            
            return model;
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Cozinha toEntity(CozinhaInputDTO cozinha) {
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
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    @Override
    public CollectionModel<CozinhaDTO> toCollectionModel(Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CozinhaController.class)
                .withSelfRel());
    }
}
