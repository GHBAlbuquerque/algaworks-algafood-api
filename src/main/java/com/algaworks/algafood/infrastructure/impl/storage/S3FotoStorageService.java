package com.algaworks.algafood.infrastructure.impl.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.infrastructure.exception.StorageException;
import com.algaworks.algafood.infrastructure.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
@Qualifier("S3")
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            var path = getArquivoPath(novaFoto.getNomeArquivo());
            byte[] bytes = IOUtils.toByteArray(novaFoto.getInputStream());
            var byteArrayInputStream = new ByteArrayInputStream(bytes);

            var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(novaFoto.getMediaType());
            //setar o content type evita que seja um octet-stream na amazon, permitindo sua visualização no navegador ao abrir a url
            objectMetaData.setContentLength(bytes.length);

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    path,
                    byteArrayInputStream,
                    objectMetaData)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (RuntimeException | IOException ex) {
            throw new StorageException("Não foi possível enviar o arquivo para Amazon S3.", ex.getCause());
        }
    }

    @Override
    public void deletar(String nomeArquivo) {

    }

    private String getArquivoPath(String nomeArquivo){
        // catalogo/arquivo.jpeg
        return String.format("%s/%s", storageProperties.getS3().getDir(), nomeArquivo);
    }
}
