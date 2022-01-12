package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Usuario;

public interface UsuarioRepository {
	List<Usuario> listar();
	Usuario buscar(Long id);
	Usuario salvar(Usuario usuario);
	void remover(Usuario usuario);
}
