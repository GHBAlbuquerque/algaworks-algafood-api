package com.algaworks.algafood.domain.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete")
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cozinha cozinha;

    @Column(nullable = false)
    private boolean ativo;

    @Column(nullable = false)
    private boolean aberto;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    // opcional, apenas usado para customizacao
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private Set<Produto> produtos = new HashSet<>();

    @Embedded
    private Endereco endereco;

    @ManyToMany
    @JoinTable(name = "restaurante_responsavel", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();
    ;

    public void ativar() {
        setAtivo(true);
    }

    public void desativar() {
        setAtivo(false);
    }

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

}
