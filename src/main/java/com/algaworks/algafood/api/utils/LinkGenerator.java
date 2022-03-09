package com.algaworks.algafood.api.utils;

import com.algaworks.algafood.api.controller.PedidoController;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class LinkGenerator {

    public static final TemplateVariables PAGEABLE_VARIABLES = new TemplateVariables(
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM));

    public Link linkToPedidosPesquisar(){
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
