package com.algaworks.algafood.infrastructure.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    FotoRecuperada recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void deletar(String nomeArquivo);

    default void substituir(String nomeArquivoExistente, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (StringUtils.isNotBlank(nomeArquivoExistente))
            this.deletar(nomeArquivoExistente);
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

    @Getter
    @Setter
    @Builder
    class FotoRecuperada {
        private InputStream inputStream;
        private String URL;

        public boolean temUrl(){
            return StringUtils.isNotBlank(getURL());
        }
    }
}
