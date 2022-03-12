package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto saveFoto(FotoProduto foto);

    void deleteFoto(FotoProduto foto);
}
