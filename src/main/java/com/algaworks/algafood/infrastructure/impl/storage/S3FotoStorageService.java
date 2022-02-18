package com.algaworks.algafood.infrastructure.impl.storage;

import com.algaworks.algafood.infrastructure.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Qualifier("S3")
public class S3FotoStorageService implements FotoStorageService {

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

    }

    @Override
    public void deletar(String nomeArquivo) {

    }
}
