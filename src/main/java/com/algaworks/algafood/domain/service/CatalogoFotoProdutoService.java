package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.entitynotfound.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    // ^ por ser uma entidade agregada, fotoProduto usa o mesmo repositório de produto.

    @Autowired
    private FotoStorageService fotoStorageService;

    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(produtoId, restauranteId));
    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        var restaurantId = foto.getRestauranteId();
        var produtoId = foto.getProdutoId();
        String nomeArquivoExistente = null;
        var novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());

        var fotoExistente = produtoRepository.findFotoById(restaurantId, produtoId);

        if (fotoExistente.isPresent()){
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.deleteFoto(fotoExistente.get());
        };

        foto.setNomeArquivo(novoNomeArquivo);
        var fotoSalva = produtoRepository.saveFoto(foto);
        produtoRepository.flush(); //descarrega assim que deu certo e segue

        var novaFoto = FotoStorageService.NovaFoto.builder()
                .inputStream(dadosArquivo)
                .nomeArquivo(foto.getNomeArquivo())
                .mediaType(foto.getContentType())
                .build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return fotoSalva;
    }

    public FotoStorageService.FotoRecuperada recuperar(String nomeArquivo){
        return fotoStorageService.recuperar(nomeArquivo);
    }

    public void deletar(Long idRestaurante, Long idProduto) {
        try {
            var fotoExistente = buscar(idRestaurante, idProduto);

            produtoRepository.deleteFoto(fotoExistente);
            produtoRepository.flush();

            fotoStorageService.deletar(fotoExistente.getNomeArquivo());

        } catch (EmptyResultDataAccessException e) {
            throw new FotoProdutoNaoEncontradaException(idProduto, idRestaurante);
        }
    }
}
