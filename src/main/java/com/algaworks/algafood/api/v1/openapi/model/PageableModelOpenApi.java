package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("Pageable")
public class PageableModelOpenApi {

    private Integer page;
    private Integer size;
    private List<String> sort;
}
