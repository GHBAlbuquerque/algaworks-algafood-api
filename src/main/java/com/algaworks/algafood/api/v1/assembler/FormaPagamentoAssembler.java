package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.v1.model.output.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.utils.LinkGenerator;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class FormaPagamentoAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkGenerator linkGenerator;

    public FormaPagamentoAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
    }

    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        try {
            var model = modelMapper.map(formaPagamento, FormaPagamentoDTO.class);

            model.add(linkGenerator.linkToFormaPagamento(model.getId()),
                    linkGenerator.linkToFormasPagamento());

            return model;
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public FormaPagamento toEntity(FormaPagamentoInputDTO formaPagamento) {
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
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    @Override
    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(FormaPagamentoController.class)
                        .withSelfRel());
    }
}
