package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Pedido;

public interface PedidoRepository {
	List<Pedido> listar();
	Pedido buscar(Long id);
	Pedido salvar(Pedido pedido);
	void remover(Pedido pedido);
}
