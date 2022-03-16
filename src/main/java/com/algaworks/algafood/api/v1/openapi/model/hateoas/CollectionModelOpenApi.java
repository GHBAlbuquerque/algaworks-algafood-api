package com.algaworks.algafood.api.v1.openapi.model.hateoas;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Setter
@Getter
@ApiModel("CollectionModel")
public class CollectionModelOpenApi<T> {

    private RepresentationModelEmbedded<T> _embedded;
    private Links _links;

    @Data
    @ApiModel("RepresentationModelEmbedded")
    private class RepresentationModelEmbedded<T> {
        private List<T> models;
    }


}
