package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.PermissaoInputDTO;
import com.algaworks.algafood.api.model.saida.PermissaoDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO convertToModel(Permissao permissao) {
        try {
            return modelMapper.map(permissao, PermissaoDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Permissao convertToEntity(PermissaoInputDTO permissao) {
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
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }

    public List<PermissaoDTO> convertListToModel(Collection<Permissao> permissoes) {
        return permissoes.stream().map(this::convertToModel).collect(Collectors.toList());
    }
}
