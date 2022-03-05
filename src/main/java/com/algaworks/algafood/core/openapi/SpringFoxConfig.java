package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30) //classe do springfox que representa a configuracao da API para gerar a doc
                    .select() //builder
                    .apis(RequestHandlerSelectors.any()) //seletor de endpoints, onde posso filtrar
                    .build();
    }
}
