package com.algaworks.algafood.api.model.saida;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CidadeDTO {

    private Long id;

    private String nome;

    private EstadoDTO estado;

}
