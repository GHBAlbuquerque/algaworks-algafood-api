package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.input.RestauranteInputDTO;
import com.algaworks.algafood.api.v1.model.output.RestauranteDTO;
import com.algaworks.algafood.api.v1.model.output.RestauranteModel;
import com.algaworks.algafood.api.v1.model.output.RestauranteSingletonFilterDTO;
import com.algaworks.algafood.api.v1.model.output.RestauranteSingletonDTO;
import com.algaworks.algafood.api.v1.utils.LinkGenerator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.exception.ConversaoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class RestauranteAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkGenerator linkGenerator;

    @Autowired
    private AlgaSecurity algaSecurity;


    public RestauranteAssembler() {
        super(RestauranteController.class, RestauranteDTO.class);
    }

    public RestauranteDTO toModel(Restaurante restaurante) {
        try {
            var model = modelMapper.map(restaurante, RestauranteDTO.class);

            model.add(linkGenerator.linkToRestaurante(model.getId()),
                    linkGenerator.linkToRestaurantes());

            var cozinha = model.getCozinha();

            cozinha.add(linkGenerator.linkToCozinha(cozinha.getId()),
                    linkGenerator.linkToCozinhas());

            return model;
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.", ex.getCause());
        }
    }

    public RestauranteSingletonFilterDTO convertToSingletonFilterModel(Restaurante restaurante) {
        try {
            var model = modelMapper.map(restaurante, RestauranteSingletonFilterDTO.class);
            var id = model.getId();

            model.add(linkGenerator.linkToRestaurante(id),
                    linkGenerator.linkToRestaurantes(),
                    linkGenerator.linkToFormasPagamentoRestaurante(id),
                    linkGenerator.linkToProdutos(id),
                    linkGenerator.linkToResponsaveisRestaurante(id));

            var linkAtivacao = adicionarLinkAtivacao(model);
            var linkAbertura = adicionarLinkAbertura(model);

            if (linkAtivacao != null) {
                model.add(linkAtivacao);
            }

            if (linkAbertura != null) {
                model.add(linkAbertura);
            }

            var cozinha = model.getCozinha();

            cozinha.add(linkGenerator.linkToCozinha(cozinha.getId()),
                    linkGenerator.linkToCozinhas());

            var usuarios = model.getResponsaveis();

            usuarios.forEach((usuario) -> usuario.add(
                    linkGenerator.linkToUsuario(usuario.getId()),
                    linkGenerator.linkToUsuarios()));

            var formasPagamento = model.getFormasPagamento();

            formasPagamento.forEach((formaPagamento) -> formaPagamento.add(
                    linkGenerator.linkToFormaPagamento(formaPagamento.getId()),
                    linkGenerator.linkToFormasPagamento()));

            var produtos = model.getProdutos();

            produtos.forEach((produto) -> produto.add(
                    linkGenerator.linkToProduto(produto.getId(), restaurante.getId()),
                    linkGenerator.linkToProdutos(restaurante.getId())));

            return model;
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.", ex.getCause());
        }
    }

    public RestauranteSingletonDTO convertToSingletonModel(Restaurante restaurante) {
        try {
            var model = modelMapper.map(restaurante, RestauranteSingletonDTO.class);
            var id = model.getId();

            model.add(linkGenerator.linkToRestaurante(id),
                    linkGenerator.linkToRestaurantes(),
                    linkGenerator.linkToFormasPagamentoRestaurante(id),
                    linkGenerator.linkToProdutos(id),
                    linkGenerator.linkToResponsaveisRestaurante(id));

            var linkAtivacao = adicionarLinkAtivacao(model);
            var linkAbertura = adicionarLinkAbertura(model);

            if (linkAtivacao != null) {
                model.add(linkAtivacao);
            }

            if (linkAbertura != null) {
                model.add(linkAbertura);
            }

            var cozinha = model.getCozinha();

            cozinha.add(linkGenerator.linkToCozinha(cozinha.getId()),
                    linkGenerator.linkToCozinhas());

            var usuarios = model.getResponsaveis();

            usuarios.forEach((usuario) -> usuario.add(
                    linkGenerator.linkToUsuario(usuario.getId()),
                    linkGenerator.linkToUsuarios()));

            var formasPagamento = model.getFormasPagamento();

            formasPagamento.forEach((formaPagamento) -> formaPagamento.add(
                    linkGenerator.linkToFormaPagamento(formaPagamento.getId()),
                    linkGenerator.linkToFormasPagamento()));

            var produtos = model.getProdutos();

            produtos.forEach((produto) -> produto.add(
                    linkGenerator.linkToProduto(produto.getId(), restaurante.getId()),
                    linkGenerator.linkToProdutos(restaurante.getId())));

            return model;
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter a entidade para um objeto de saída.", ex.getCause());
        }
    }

    public Restaurante toEntity(RestauranteInputDTO restaurante) {
        try {
            return modelMapper.map(restaurante, Restaurante.class);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    public void copyToInstance(RestauranteInputDTO restauranteInput, Restaurante restaurante) {
        try {
            if (ObjectUtils.isNotEmpty(restauranteInput.getCozinhaId())) {
                restaurante.setCozinha(new Cozinha());
            }

            if (ObjectUtils.isNotEmpty(restauranteInput.getCidade())) {
                restaurante.getEndereco().setCidade(new Cidade());
            }

            modelMapper.map(restauranteInput, restaurante);
        } catch (IllegalArgumentException ex) {
            throw new ConversaoException("Erro ao converter o objeto de input para entidade.", ex.getCause());
        }
    }

    @Override
    public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(RestauranteController.class)
                        .withSelfRel());
    }

    private Link adicionarLinkAtivacao(RestauranteModel model) {

        if (algaSecurity.podeGerenciarRestaurante(model.getId())) {
            if (!model.isAtivo()) {
                return linkGenerator.linkToRestauranteAtivar(model.getId());
            }

            if (model.isAtivo()) {
                return linkGenerator.linkToRestauranteDesativar(model.getId());
            }
        }

        return null;

    }

    private Link adicionarLinkAbertura(RestauranteModel model) {

        if (algaSecurity.podeGerenciarRestaurante(model.getId())) {
            if (!model.isAberto()) {
                return linkGenerator.linkToRestauranteAbrir(model.getId());
            }

            if (model.isAberto()) {
                return linkGenerator.linkToRestauranteFechar(model.getId());
            }
        }

        return null;
    }
}
