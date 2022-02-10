package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.model.saida.FormaPagamentoDTO;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO convertToModel(FormaPagamento formaPagamento) {
        try {
            return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public FormaPagamento convertToEntity(FormaPagamentoInputDTO formaPagamento) {
        try {
            return modelMapper.map(formaPagamento, FormaPagamento.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }

    public void copyToInstance(FormaPagamentoInputDTO formaPagamentoInput, FormaPagamento formaPagamento) {
        try {
            modelMapper.map(formaPagamentoInput, formaPagamento);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.",  ex.getCause());
        }
    }
}
