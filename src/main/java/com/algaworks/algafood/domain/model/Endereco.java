package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algafood.validation.Groups;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor

public class Endereco {

	@NotBlank
	@Column(name = "endereco_logradouro", nullable=false)
	private String logradouro;

	@NotBlank
	@Column(name = "endereco_numero", nullable=false)
	private String numero;

	@Column(name = "endereco_complemento")
	private String complemento;

	@NotBlank
	@Column(name = "endereco_bairro", nullable=false)
	private String bairro;

	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CadastroRestaurante.class)
	@ManyToOne
	@JoinColumn(name = "endereco_cidade_id", nullable=false)
	private Cidade cidade;

	@NotBlank
	@Column(name = "endereco_cep", nullable=false)
	private String cep;
}
