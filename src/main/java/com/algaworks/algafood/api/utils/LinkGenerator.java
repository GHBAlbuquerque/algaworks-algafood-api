package com.algaworks.algafood.api.utils;

import com.algaworks.algafood.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LinkGenerator {

    public static final TemplateVariables PAGEABLE_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM));

    public Link linkToCidade(Long cidadeId) {
        return linkTo(methodOn(CidadeController.class)
                .buscar(cidadeId)).withSelfRel();
    }

    public Link linkToCidades() {
        return linkTo(methodOn(CidadeController.class)
                .listar())
                .withRel(IanaLinkRelations.COLLECTION);
    }

    public Link linkToCozinha(Long cozinhaId) {
        return linkTo(methodOn(CozinhaController.class)
                .buscar(cozinhaId)).withSelfRel();
    }

    public Link linkToCozinhas() {
        return linkTo(methodOn(CozinhaController.class)
                .listar())
                .withRel(IanaLinkRelations.COLLECTION);
    }

    public Link linkToEstado(Long estadoId) {
        return linkTo(methodOn(EstadoController.class)
                .buscar(estadoId)).withSelfRel();
    }

    public Link linkToEstados() {
        return linkTo(methodOn(EstadoController.class)
                .listar())
                .withRel(IanaLinkRelations.COLLECTION);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoId)).withSelfRel();
    }

    public Link linkToFormasPagamento() {
        return linkTo(methodOn(FormaPagamentoController.class)
                .listar())
                .withRel(IanaLinkRelations.COLLECTION);
    }

    public Link linkToFotoProduto(Long produtoId, Long restauranteId) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class)
                .buscar(restauranteId, produtoId)).withSelfRel();
    }

    public Link linkToGrupo(Long grupoId) {
        return linkTo(methodOn(GrupoController.class)
                .buscar(grupoId)).withSelfRel();
    }

    public Link linkToGrupos() {
        return linkTo(methodOn(GrupoController.class)
                .listar())
                .withRel(IanaLinkRelations.COLLECTION);
    }

    public Link linkToPermissao(Long permissaoId) {
        return linkTo(methodOn(PermissaoController.class)
                .buscar(permissaoId)).withSelfRel();
    }

    public Link linkToPermissoes() {
        return linkTo(methodOn(PermissaoController.class)
                .listar())
                .withRel(IanaLinkRelations.COLLECTION);
    }

    public Link linkToProduto(Long produtoId, Long restauranteId) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId)).withSelfRel();
    }

    public Link linkToProdutos(Long restauranteId) {
        var url = linkTo(methodOn(RestauranteProdutoController.class)
                .listar(restauranteId, false)).toUri().toString();

        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("incluirInativos", TemplateVariable.VariableType.REQUEST_PARAM));

        return Link.of(UriTemplate.of(url, filtroVariables),
                LinkRelation.of("pesquisar"));
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteId, null)).withSelfRel();
    }

    public Link linkToRestaurantes() {
        return linkTo(methodOn(RestauranteController.class)
                .listar())
                .withRel(IanaLinkRelations.COLLECTION);
    }

    public Link linkToUsuario(Long usuarioId) {
        return linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioId)).withSelfRel();
    }

    public Link linkToUsuarios() {
        var url = linkTo(methodOn(UsuarioController.class)
                .listar(null)).toUri().toString();

        return Link.of(UriTemplate.of(url, PAGEABLE_VARIABLES),
                LinkRelation.of("pesquisar"));
    }

    public Link linkToPedido(String codigo) {
        return linkTo(methodOn(PedidoController.class)
                .buscar(codigo)).withSelfRel();
    }

    public Link linkToPedidos() {
        return linkTo(methodOn(PedidoController.class)
                .listar())
                .withRel(IanaLinkRelations.COLLECTION);
    }


    public Link linkToPedidosPesquisar() {
        var pedidosUrl = linkTo(PedidoController.class).toUri().toString() + "/pesquisar";

        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("status", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM));


        return Link.of(UriTemplate.of(pedidosUrl, PAGEABLE_VARIABLES.concat(filtroVariables)),
                LinkRelation.of("pesquisar"));
    }

}
