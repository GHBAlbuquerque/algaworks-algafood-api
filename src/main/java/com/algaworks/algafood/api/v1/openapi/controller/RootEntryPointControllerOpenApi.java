package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.controller.RootEntryPointController;
import io.swagger.annotations.Api;

@Api(tags = "Root Entry Point")
public interface RootEntryPointControllerOpenApi {

    public RootEntryPointController.RootEntryPointModel root();
}
