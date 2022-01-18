package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {
	
	List<Restaurante> buscarComFreteGratis(String nome); 

}
