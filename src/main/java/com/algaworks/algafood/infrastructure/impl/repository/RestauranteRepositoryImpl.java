package com.algaworks.algafood.infrastructure.impl.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.algaworks.algafood.infrastructure.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    //Referencia ciruclar. O repo implementa o Queries, mas Queries usa o repo.
    @Autowired
    @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> buscarComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }

}
