package com.algaworks.algafood.api.v1.model.output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestauranteSingletonDTO extends RepresentationModel<RestauranteSingletonDTO> implements RestauranteModel {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaDTO cozinha;

    private boolean ativo;

    private boolean aberto;

    private EnderecoDTO endereco;

    private Set<UsuarioDTO> responsaveis;

    private Set<FormaPagamentoDTO> formasPagamento;

    private Set<ProdutoDTO> produtos;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Boolean isAtivo() {
        return this.ativo;
    }

    @Override
    public Boolean isAberto() {
        return this.aberto;
    }

}
