package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Produto;

public interface ProdutoRepository {
	List<Produto> listar();
	Produto buscar(Long id);
	Produto salvar(Produto produto);
	void remover(Produto prduto);
}
