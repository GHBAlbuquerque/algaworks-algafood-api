package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.entrada.GrupoEntradaDTO;
import com.algaworks.algafood.api.model.saida.GrupoDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO convertToModel(Grupo grupo) {
        try {
            return modelMapper.map(grupo, GrupoDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Grupo convertToEntity(GrupoEntradaDTO grupo) {
        try {
            return modelMapper.map(grupo, Grupo.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.");
        }
    }


    public void copyToInstance(GrupoEntradaDTO grupoEntrada, Grupo grupo) {
        try {
            modelMapper.map(grupoEntrada, grupo);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de entrada para entidade.",  ex.getCause());
        }
    }
}
