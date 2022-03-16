package com.algaworks.algafood.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private BigDecimal precoTotal;

    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    public void definirPrecoTotal() {
        definirPrecoUnitario();
        this.precoTotal = this.precoUnitario
                .multiply(BigDecimal.valueOf(this.quantidade));
    }

    private void definirPrecoUnitario() {
        this.precoUnitario = this.produto.getPreco();
    }

}
