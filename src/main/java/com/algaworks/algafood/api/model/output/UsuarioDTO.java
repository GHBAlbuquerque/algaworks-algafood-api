package com.algaworks.algafood.api.model.output;

import com.algaworks.algafood.api.model.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    @JsonView({PedidoView.PedidoSimpleDTO.class,PedidoView.PedidoIdentificationDTO.class})
    private Long id;

    private String nome;

    private String email;
}
