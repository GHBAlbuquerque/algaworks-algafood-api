package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.validation.Groups;
import com.algaworks.algafood.validation.annotations.Hamburgueria;
import com.algaworks.algafood.validation.annotations.Multiplo;
import com.algaworks.algafood.validation.annotations.TaxaFrete;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Hamburgueria(idField = "cozinha.id", nomeField="nome", nomeObrigatorio="- Hamburgueria")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@TaxaFrete(groups = {Groups.TaxaFrete1.class, Default.class})
	@Multiplo(numero = 2, groups = {Groups.TaxaFrete2.class, Default.class})
	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;

	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CadastroRestaurante.class)
	@NotNull
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
