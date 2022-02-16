package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class FotoProduto {
	
	@Id
	@EqualsAndHashCode.Include
	@Column(name = "produto_id") //mapeio no id da entidade, a entidade mãe
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Produto produto;
	
	@Column(nullable = false)
	private String nomeArquivo;
	
	private String descricao;
	
	@Column(nullable = false)
	private String contentType;
	
	private Long tamanho;

}
