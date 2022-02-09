package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.UsuarioEntradaDTO;
import com.algaworks.algafood.api.model.entrada.UsuarioNovoEntradaDTO;
import com.algaworks.algafood.api.model.saida.UsuarioDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO convertToModel(Usuario usuario) {
        try {
            return modelMapper.map(usuario, UsuarioDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Usuario convertToEntity(UsuarioNovoEntradaDTO usuarioEntrada) {
        try {
            return modelMapper.map(usuarioEntrada, Usuario.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.");
        }
    }

    public void copyToInstance(UsuarioNovoEntradaDTO usuarioEntrada, Usuario usuario) {
        try {
            modelMapper.map(usuarioEntrada, usuario);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }

    public void copyToInstance(UsuarioEntradaDTO usuarioEntrada, Usuario usuario) {
        try {
            modelMapper.map(usuarioEntrada, usuario);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }
}
