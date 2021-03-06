package com.algaworks.algafood.infrastructure.impl.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            var pathArquivo = getArquivoPath(nomeArquivo);
            var inputStream = Files.newInputStream(pathArquivo);

            var fotoRecuperada =
                    FotoRecuperada.builder()
                            .inputStream(inputStream).build();

            return fotoRecuperada;
        } catch (IOException e) {
            throw new StorageException("Não foi possível recuperar o arquivo.", e.getCause());
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

        var arquivo = novaFoto.getInputStream();
        var nomeArquivo = novaFoto.getNomeArquivo();

        var pathArquivo = getArquivoPath(nomeArquivo);

        try {
            FileCopyUtils.copy(arquivo, Files.newOutputStream(pathArquivo));
        } catch (IOException e) {
            throw new StorageException("Não foi possível armazenar o arquivo.", e.getCause());
        }
    }

    @Override
    public void deletar(String nomeArquivo) {
        try {
            var pathArquivo = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(pathArquivo);
        } catch (IOException e) {
            throw new StorageException("Não foi possível deletar o arquivo.", e.getCause());
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDir()
                .resolve(Path.of(nomeArquivo));
    }
}
