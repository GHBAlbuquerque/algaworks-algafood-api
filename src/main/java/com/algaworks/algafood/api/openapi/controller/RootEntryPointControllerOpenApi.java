package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.controller.RootEntryPointController;
import io.swagger.annotations.Api;

@Api(tags = "Root Entry Point")
public interface RootEntryPointControllerOpenApi {

    public RootEntryPointController.RootEntryPointModel root();
}
