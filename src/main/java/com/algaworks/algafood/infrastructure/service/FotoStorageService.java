package com.algaworks.algafood.infrastructure.service;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    InputStream recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void deletar(String nomeArquivo);

    default void substituir(String nomeArquivo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (StringUtils.isNotBlank(nomeArquivo))
            this.deletar(nomeArquivo);
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID() + "_" + nomeOriginal;
    }

    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
        private String mediaType;
    }
}
