package com.algaworks.algafood.infrastructure.impl.storage;

import com.algaworks.algafood.infrastructure.exception.StorageException;
import com.algaworks.algafood.infrastructure.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Qualifier("Local")
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${algafood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

    @Override
    public void armazenar(NovaFoto novaFoto) {

        var arquivo = novaFoto.getInputStream();
        var nomeArquivo = UUID.randomUUID() + "_" + novaFoto.getNomeArquivo();

        var pathArquivo = getArquivoPath(nomeArquivo);

        try {
            FileCopyUtils.copy(arquivo, Files.newOutputStream(pathArquivo));
        } catch (IOException e) {
           throw new StorageException("Não foi possível armazenar o arquivo.", e.getCause());
        }
    }

    private Path getArquivoPath(String nomeArquivo){
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }
}
