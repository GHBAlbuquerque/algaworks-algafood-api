package com.algaworks.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFilter("filtroRestaurante")
public class RestauranteSingletonDTO extends RepresentationModel<RestauranteSingletonDTO> implements RestauranteModel {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaDTO cozinha;

    private boolean ativo;

    private EnderecoDTO endereco;

    private Set<UsuarioDTO> responsaveis;

    private Set<FormaPagamentoDTO> formasPagamento;

    private Set<ProdutoDTO> produtos;

    @Override
    public Long getId(){
        return this.id;
    }

    @Override
    public Boolean isAtivo(){
        return this.ativo;
    }

}
