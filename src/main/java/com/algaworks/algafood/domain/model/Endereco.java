package com.algaworks.algafood.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	@Column(name = "endereco_logradouro", nullable=false)
	private String logradouro;

	@Column(name = "endereco_numero", nullable=false)
	private String numero;

	@Column(name = "endereco_complemento")
	private String complemento;

	@Column(name = "endereco_bairro", nullable=false)
	private String bairro;

	@ManyToOne
	@JoinColumn(name = "endereco_cidade_id", nullable=false)
	private Cidade cidade;

	@Column(name = "endereco_cep", nullable=false)
	private String cep;
}
