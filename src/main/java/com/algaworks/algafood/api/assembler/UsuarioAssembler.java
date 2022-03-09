package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.model.input.update.UsuarioUpdateDTO;
import com.algaworks.algafood.api.model.output.UsuarioDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioAssembler() {
        super(UsuarioController.class, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        try {
            var model= modelMapper.map(usuario, UsuarioDTO.class);

            model.add(linkTo(
                    methodOn(UsuarioController.class)
                            .buscar(model.getId()))
                    .withSelfRel());

            model.add(linkTo(
                    methodOn(UsuarioController.class)
                            .listar(null))
                    .withRel(IanaLinkRelations.COLLECTION));

            return model;
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Usuario toEntity(UsuarioInputDTO usuarioInput) {
        try {
            return modelMapper.map(usuarioInput, Usuario.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }

    public void copyToInstance(UsuarioInputDTO usuarioInput, Usuario usuario) {
        try {
            modelMapper.map(usuarioInput, usuario);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    public void copyToInstance(UsuarioUpdateDTO usuarioInput, Usuario usuario) {
        try {
            modelMapper.map(usuarioInput, usuario);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {

        var usuariosUrl = linkTo(UsuarioController.class).toUri().toString();

        var uriTemplate = UriTemplate.of(usuariosUrl, new TemplateVariables(
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
        ));

        var link = Link.of(uriTemplate, IanaLinkRelations.COLLECTION);

        return super.toCollectionModel(entities)
                .add(link);
    }
}
