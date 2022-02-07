package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.validation.annotations.Hamburgueria;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Hamburgueria(idField = "cozinha.id", nomeField="nome", nomeObrigatorio="- Hamburgueria")
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

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@ManyToMany
	// opcional, apenas usado para customizacao
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento;

	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	@Embedded
	//@NotNull @Valid @ConvertGroup(from = Default.class, to = Groups.CadastroRestaurante.class)
	private Endereco endereco;

	@ManyToMany
	@JoinTable(name = "restaurante_responsavel", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private List<Usuario> responsaveis;

}
