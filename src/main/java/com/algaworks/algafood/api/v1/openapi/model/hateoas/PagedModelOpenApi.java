package com.algaworks.algafood.api.v1.openapi.model.hateoas;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("PagedModel")
public class PagedModelOpenApi {

    private Long size;
    private Long totalElements;
    private Long totalPages;
    private Long number;
}
