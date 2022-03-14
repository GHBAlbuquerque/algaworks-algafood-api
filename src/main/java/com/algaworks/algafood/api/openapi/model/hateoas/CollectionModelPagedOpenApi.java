package com.algaworks.algafood.api.openapi.model.hateoas;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("CollectionModel - Paged")
public class CollectionModelPagedOpenApi<T> extends CollectionModelOpenApi<T>{

    private PagedModelOpenApi page;
}
