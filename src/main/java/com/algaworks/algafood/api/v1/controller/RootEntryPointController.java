package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.openapi.controller.RootEntryPointControllerOpenApi;
import com.algaworks.algafood.api.v1.utils.LinkGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController implements RootEntryPointControllerOpenApi {

    @Autowired
    private LinkGenerator linkGenerator;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(
                linkGenerator.linkToCidades().withRel("cidades"),
                linkGenerator.linkToCozinhas().withRel("cozinhas"),
                linkGenerator.linkToEstados().withRel("estados"),
                linkGenerator.linkToFormasPagamento().withRel("formas-pagamento"),
                linkGenerator.linkToGrupos().withRel("grupos"),
                linkGenerator.linkToPedidos().withRel("pedidos"),
                linkGenerator.linkToPermissoes().withRel("permissoes"),
                linkGenerator.linkToRestaurantes().withRel("restaurantes"),
                linkGenerator.linkToUsuarios().withRel("usuarios"),
                linkGenerator.linkToEstatisticas()
        );

        return rootEntryPointModel;
    }

    public class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

    }
}
