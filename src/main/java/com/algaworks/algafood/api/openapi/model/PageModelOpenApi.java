package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("Page")
public class PageModelOpenApi<T> {

    private List<T> content;
    private Long size;
    private Long totalElements;
    private Long totalPages;
    private Long number;

}
