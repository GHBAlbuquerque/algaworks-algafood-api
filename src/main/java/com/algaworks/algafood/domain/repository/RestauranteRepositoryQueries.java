package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepositoryQueries {
	
	List<Restaurante> buscarComFreteGratis(String nome); 

}
