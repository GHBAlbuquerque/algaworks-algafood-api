package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("Links")
public class LinksModelOpenApi {

    private LinkModel rel;

    private class LinkModel {
        private String href;
        private String templated;
    }
}
