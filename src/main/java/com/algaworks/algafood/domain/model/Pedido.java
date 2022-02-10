package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.enums.StatusPedidoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal subtotal;
	
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDate dataCriacao;
	
	private LocalDate dataConfirmacao;
	
	private LocalDate dataEntrega;
	
	private LocalDate dataCancelamento;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario cliente;
	
	@Embedded
	@JoinColumn(nullable = false)
	private Endereco enderecoEntrega;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusPedidoEnum status;

	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	
	

}
