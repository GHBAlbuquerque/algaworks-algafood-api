package com.algaworks.algafood.api.model.entrada;

import com.algaworks.algafood.validation.Groups;
import com.algaworks.algafood.validation.annotations.Hamburgueria;
import com.algaworks.algafood.validation.annotations.Multiplo;
import com.algaworks.algafood.validation.annotations.TaxaFrete;
import lombok.Data;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.math.BigDecimal;

@Data
@Hamburgueria(idField = "cozinha.id", nomeField="nome", nomeObrigatorio="- Hamburgueria")
public class RestauranteEntradaDTO {

    @NotBlank
    private String nome;

    @TaxaFrete(groups = {Groups.TaxaFrete1.class, Default.class})
    @Multiplo(numero = 2, groups = {Groups.TaxaFrete2.class, Default.class})
    private BigDecimal taxaFrete;

    @NotNull
    @Valid
    private CozinhaIdEntrada cozinha;

    @Embedded
    private EnderecoEntradaDTO endereco;
}
