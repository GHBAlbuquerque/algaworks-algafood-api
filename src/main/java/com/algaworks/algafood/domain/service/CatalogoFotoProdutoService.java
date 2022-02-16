package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    // ^ por ser uma entidade agregada, fotoProduto usa o mesmo repositório de produto.

    @Transactional
    public FotoProduto salvar(FotoProduto foto){
        var restaurantId = foto.getRestauranteId();
        var produtoId = foto.getProdutoId();

        var fotoExistente = produtoRepository.findFotoById(restaurantId, produtoId);

        fotoExistente.ifPresent(fotoProduto -> produtoRepository.delete(fotoProduto));

        return produtoRepository.save(foto);
    }
}
