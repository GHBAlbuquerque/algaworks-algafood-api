package com.algaworks.algafood.domain.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Cidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@NotNull(groups = {Groups.CadastroRestaurante.class})
	private Long id;

	@NotNull(groups = {Groups.CadastroCidade.class})
	@Column(nullable = false)
	private String nome;

	@NotNull(groups = {Groups.CadastroCidade.class})
	@Valid
	@ManyToOne
	@JoinColumn(nullable = false)
	private Estado estado;

}
