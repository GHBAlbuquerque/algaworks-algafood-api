package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class GenericProblem {

    @ApiModelProperty(example = "400")
    private Integer status;

    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos")
    private String type;

    @ApiModelProperty(example = "Dados Inválidos")
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;
}
