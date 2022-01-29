package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algafood.validation.Groups;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@PositiveOrZero
	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;

	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CadastroRestaurante.class)
	@NotNull
	@JsonIgnoreProperties("hibernateLazyInitializer")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Cozinha cozinha;

	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;

	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;

	@JsonIgnore
	@ManyToMany
	// opcional, apenas usado para customizacao
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento;

	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

	@JsonIgnore
	@Embedded
	//@NotNull @Valid @ConvertGroup(from = Default.class, to = Groups.CadastroRestaurante.class)
	private Endereco endereco;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_responsavel", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private List<Usuario> responsaveis;

}
