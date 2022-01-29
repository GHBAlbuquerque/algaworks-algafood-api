package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor

public class Endereco {

	@NotNull
	@Column(name = "endereco_logradouro", nullable=false)
	private String logradouro;

	@NotNull
	@Column(name = "endereco_numero", nullable=false)
	private String numero;

	@Column(name = "endereco_complemento")
	private String complemento;

	@NotNull
	@Column(name = "endereco_bairro", nullable=false)
	private String bairro;

	@Valid
	@ManyToOne
	@JoinColumn(name = "endereco_cidade_id", nullable=false)
	private Cidade cidade;

	@NotNull
	@Column(name = "endereco_cep", nullable=false)
	private String cep;
}
