package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.PermissaoController;
import com.algaworks.algafood.api.model.input.PermissaoInputDTO;
import com.algaworks.algafood.api.model.output.PermissaoDTO;
import com.algaworks.algafood.api.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkGenerator linkGenerator;

    public PermissaoAssembler() {
        super(PermissaoController.class, PermissaoDTO.class);
    }

    public PermissaoDTO toModel(Permissao permissao) {
        try {
            var model = modelMapper.map(permissao, PermissaoDTO.class);

            model.add(linkGenerator.linkToPermissao(model.getId()),
                    linkGenerator.linkToPermissoes());

            return model;
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Permissao toEntity(PermissaoInputDTO permissao) {
        try {
            return modelMapper.map(permissao, Permissao.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }


    public void copyToInstance(PermissaoInputDTO permissaoInput, Permissao permissao) {
        try {
            modelMapper.map(permissaoInput, permissao);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    @Override
    public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
        return super.toCollectionModel(entities)
                .add(linkGenerator.linkToPermissoes());
    }
}
