package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.enums.StatusPedidoEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate dataCriacao;

    private LocalDate dataConfirmacao;

    private LocalDate dataEntrega;

    private LocalDate dataCancelamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario cliente;

    @Embedded
    @JoinColumn(nullable = false)
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedidoEnum status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    public void calcularValorTotal() {
        this.subtotal = itens.stream().map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (ObjectUtils.isEmpty(taxaFrete)) definirFrete();

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void definirFrete() {
        this.taxaFrete = getRestaurante().getTaxaFrete();
    }

    public void atribuirPedidoAosItens() {
        itens.forEach(item -> item.setPedido(this));
    }
}
