package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.input.EstadoInputDTO;
import com.algaworks.algafood.api.model.output.EstadoDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class EstadoAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoAssembler() {
        super(EstadoController.class, EstadoDTO.class);
    }

    public EstadoDTO toModel(Estado estado) {
        try {
            var model = modelMapper.map(estado, EstadoDTO.class);

            model.add(linkTo(
                    methodOn(EstadoController.class)
                            .buscar(model.getId()))
                    .withSelfRel());

            model.add(linkTo(
                    methodOn(EstadoController.class)
                            .listar())
                    .withRel(IanaLinkRelations.COLLECTION));

            return model;

        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Estado toEntity(EstadoInputDTO estado) {
        try {
            return modelMapper.map(estado, Estado.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }


    public void copyToInstance(EstadoInputDTO estadoInput, Estado estado) {
        try {
            modelMapper.map(estadoInput, estado);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    @Override
    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(EstadoController.class)
                        .withSelfRel());
    }
}
