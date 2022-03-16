package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.model.input.GrupoInputDTO;
import com.algaworks.algafood.api.v1.model.output.GrupoDTO;
import com.algaworks.algafood.api.v1.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkGenerator linkGenerator;

    public GrupoAssembler() {
        super(GrupoController.class, GrupoDTO.class);
    }

    public GrupoDTO toModel(Grupo grupo) {
        try {
            var model = modelMapper.map(grupo, GrupoDTO.class);

            model.add(linkGenerator.linkToGrupo(model.getId()),
                    linkGenerator.linkToGrupos(),
                    linkGenerator.linkToGrupoPermissoes(grupo.getId()));

            var permissoes = model.getPermissoes();

            permissoes.forEach(permissao -> permissao.add(linkGenerator.linkToPermissao(grupo.getId()),
                    linkGenerator.linkToPermissoes(),
                    linkGenerator.linkToGrupoPermissaoRemover(grupo.getId(), permissao.getId())));

            return model;
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Grupo toEntity(GrupoInputDTO grupo) {
        try {
            return modelMapper.map(grupo, Grupo.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }


    public void copyToInstance(GrupoInputDTO grupoInput, Grupo grupo) {
        try {
            modelMapper.map(grupoInput, grupo);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    @Override
    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities).add(linkGenerator.linkToGrupos());
    }

}
