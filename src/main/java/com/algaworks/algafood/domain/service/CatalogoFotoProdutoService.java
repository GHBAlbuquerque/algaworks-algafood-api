package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.infrastructure.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    // ^ por ser uma entidade agregada, fotoProduto usa o mesmo repositório de produto.

    @Autowired
    @Qualifier("Local")
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        var restaurantId = foto.getRestauranteId();
        var produtoId = foto.getProdutoId();
        String nomeArquivoExistente = null;
        var novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());

        var fotoExistente = produtoRepository.findFotoById(restaurantId, produtoId);

        if (fotoExistente.isPresent()){
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        };

        foto.setNomeArquivo(novoNomeArquivo);
        var fotoSalva = produtoRepository.save(foto);
        produtoRepository.flush(); //descarrega assim que deu certo e segue

        var novaFoto = FotoStorageService.NovaFoto.builder()
                .inputStream(dadosArquivo)
                .nomeArquivo(foto.getNomeArquivo())
                .build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return fotoSalva;
    }
}