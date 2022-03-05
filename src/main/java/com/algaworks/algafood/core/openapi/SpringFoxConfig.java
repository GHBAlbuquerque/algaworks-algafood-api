package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30) //classe do springfox que representa a configuracao da API para gerar a doc
                    .select() //builder
                    .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //seletor de endpoints, onde posso filtrar
                    //.paths(PathSelectors.ant("/restaurantes/*"))
                    .build()
                .apiInfo(apiInfo());
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Algafood API")
                .description("Api aberta para clientes e restaurantes da Aplicação Algafood")
                .contact(new Contact("Giovanna Albuquerque", "https://github.com/GHBAlbuquerque",
                        "ghb.albuquerque@gmail.com"))
                .build();
    }
}
