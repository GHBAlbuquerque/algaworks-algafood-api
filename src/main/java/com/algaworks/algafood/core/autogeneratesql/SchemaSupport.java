package com.algaworks.algafood.core.autogeneratesql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Limpa auto-generated ssql.sql antes do JPA gerá-lo.
 */
@Configuration
public class SchemaSupport {

    @Bean
    public static BeanFactoryPostProcessor limpadorSchemaDdlProcessor() {
        return beanFactory -> {
            try {
                Files.deleteIfExists(Path.of("src/main/resources/db/scripts/ddl.sql"));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        };
    }
}