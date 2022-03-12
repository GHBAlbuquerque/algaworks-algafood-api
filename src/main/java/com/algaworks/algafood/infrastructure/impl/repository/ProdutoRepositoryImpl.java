package com.algaworks.algafood.infrastructure.impl.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public FotoProduto saveFoto(FotoProduto foto) {
        return manager.merge(foto);
    }

    @Override
    @Transactional
    public void deleteFoto(FotoProduto foto) {
        manager.remove(foto);
    }
}
