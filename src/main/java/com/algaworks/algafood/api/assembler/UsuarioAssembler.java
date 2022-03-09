package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.model.input.update.UsuarioUpdateDTO;
import com.algaworks.algafood.api.model.output.UsuarioDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO toModel(Usuario usuario) {
        try {
            return modelMapper.map(usuario, UsuarioDTO.class);
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
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }

    public void copyToInstance(UsuarioUpdateDTO usuarioInput, Usuario usuario) {
        try {
            modelMapper.map(usuarioInput, usuario);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }

    public List<UsuarioDTO> toCollectionModel(Collection<Usuario> usuarios) {
        return usuarios.stream().map(this::toModel).collect(Collectors.toList());
    }
}
