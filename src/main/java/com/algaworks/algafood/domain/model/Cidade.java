package com.algaworks.algafood.domain.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

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

	@NotNull
	@Column(nullable = false)
	private String nome;

	@NotNull
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CadastroCidade.class)
	@ManyToOne
	@JoinColumn(nullable = false)
	private Estado estado;

}
