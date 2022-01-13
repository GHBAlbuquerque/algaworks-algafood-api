package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false)
	private String codigo;
	
	private BigDecimal subtotal;
	
	private BigDecimal taxaFrete;
	
	private BigDecimal valorTotal;
	
	private LocalDate dataCriacao;
	
	private LocalDate dataConfirmacao;
	
	private LocalDate dataEntrega;
	
	private LocalDate dataCancelamento;
	
	@OneToOne
	private Restaurante restaurante;
	
	//private StatusPedidoEnum status;
	
	//private Usuario cliente;
	
	//private Endereco enderecoEntrega;
	
	//private List<ItemPedido> itens;
	
	
	

}
