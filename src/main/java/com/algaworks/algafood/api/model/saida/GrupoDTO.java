package com.algaworks.algafood.api.model.saida;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class GrupoDTO {

    private Long id;

    private String nome;

    private List<PermissaoDTO> permissoes;
}
