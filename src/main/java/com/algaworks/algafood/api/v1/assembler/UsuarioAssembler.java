package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.v1.model.input.update.UsuarioUpdateDTO;
import com.algaworks.algafood.api.v1.model.output.UsuarioDTO;
import com.algaworks.algafood.api.v1.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UsuarioAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkGenerator linkGenerator;

    public UsuarioAssembler() {
        super(UsuarioController.class, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        try {
            var model = modelMapper.map(usuario, UsuarioDTO.class);

            model.add(linkGenerator.linkToUsuario(model.getId()),
                    linkGenerator.linkToUsuarios());

            var grupos = model.getGrupos();

            grupos.forEach(grupo -> grupo.add(linkGenerator.linkToGrupo(grupo.getId()),
                    linkGenerator.linkToGrupos(),
                    linkGenerator.linkToUsuarioGruposDesassociar(usuario.getId(), grupo.getId())));


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

        var uriTemplate = UriTemplate.of(usuariosUrl, LinkGenerator.PAGEABLE_VARIABLES);

        var link = Link.of(uriTemplate, IanaLinkRelations.COLLECTION);

        return super.toCollectionModel(entities)
                .add(link);
    }

}
