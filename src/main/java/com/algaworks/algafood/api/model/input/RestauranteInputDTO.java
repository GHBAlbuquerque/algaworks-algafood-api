package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.validation.Groups;
import com.algaworks.algafood.validation.annotations.Hamburgueria;
import com.algaworks.algafood.validation.annotations.Multiplo;
import com.algaworks.algafood.validation.annotations.TaxaFrete;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.Embedded;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.math.BigDecimal;

@Data
@Hamburgueria(idField = "cozinha.id", nomeField = "nome", nomeObrigatorio = "- Hamburgueria")
public class RestauranteInputDTO {

    @NotBlank
    private String nome;

    @TaxaFrete(groups = {Groups.TaxaFrete1.class, Default.class})
    @Multiplo(numero = 2, groups = {Groups.TaxaFrete2.class, Default.class})
    private BigDecimal taxaFrete;

    @NotNull
    @JsonProperty("cozinha")
    private Long cozinhaId;

    @Embedded
    private EnderecoInputDTO endereco;

    public Long getCidade() {
        if (ObjectUtils.isNotEmpty(endereco)) return this.getEndereco().getCidadeId();

        return null;
    }
}
