package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.model.input.ProdutoInputDTO;
import com.algaworks.algafood.api.v1.model.input.update.ProdutoUpdateDTO;
import com.algaworks.algafood.api.v1.model.output.ProdutoDTO;
import com.algaworks.algafood.api.v1.utils.LinkGenerator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class ProdutoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkGenerator linkGenerator;

    @Autowired
    private AlgaSecurity algaSecurity;

    public ProdutoAssembler() {
        super(RestauranteProdutoController.class, ProdutoDTO.class);
    }

    public ProdutoDTO toModel(Produto produto) {
        try {
            var model = modelMapper.map(produto, ProdutoDTO.class);
            var restauranteId = produto.getRestaurante().getId();

            if (algaSecurity.podeConsultarRestaurantes()) {
                model.add(linkGenerator.linkToProduto(model.getId(), restauranteId),
                        linkGenerator.linkToFotoProduto(model.getId(), restauranteId).withRel("foto"));

                adicionarLinksAtivacao(model, restauranteId);
            }

            return model;

        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.");
        }
    }

    public Produto toEntity(ProdutoInputDTO produto) {
        try {
            return modelMapper.map(produto, Produto.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.");
        }
    }


    public void copyToInstance(ProdutoInputDTO produtoInput, Produto produto) {
        try {
            modelMapper.map(produtoInput, produto);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    public void copyToInstance(ProdutoUpdateDTO produtoInput, Produto produto) {
        try {
            modelMapper.map(produtoInput, produto);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    @Override
    public CollectionModel<ProdutoDTO> toCollectionModel(Iterable<? extends Produto> entities) {
        return super.toCollectionModel(entities);
    }

    private void adicionarLinksAtivacao(ProdutoDTO model, Long restauranteId) {

        if (model.getAtivo()) {
            model.add(linkGenerator.linkToProdutoDesativar(model.getId(), restauranteId));
        }

        if (!model.getAtivo()) {
            model.add(linkGenerator.linkToProdutoAtivar(model.getId(), restauranteId));
        }

    }
}
