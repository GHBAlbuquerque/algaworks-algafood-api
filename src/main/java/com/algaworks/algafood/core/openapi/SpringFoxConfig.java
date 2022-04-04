package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.v1.exceptionhandler.CustomProblem;
import com.algaworks.algafood.api.v1.exceptionhandler.GenericProblem;
import com.algaworks.algafood.api.v1.model.output.*;
import com.algaworks.algafood.api.v1.openapi.model.PageModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.hateoas.CollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.hateoas.CollectionModelPagedOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.hateoas.LinksModelOpenApi;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class) //classe com varios beans definidos que lê annotations
public class SpringFoxConfig {

    private TypeResolver typeResolver = new TypeResolver();

    @Bean
    public Docket apiDocket() {

        assert securityScheme() != null;
        return new Docket(DocumentationType.OAS_30) //classe do springfox que representa a configuracao da API para gerar a doc
                .select() //builder
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //seletor de endpoints, onde posso filtrar
                //.paths(PathSelectors.ant("/restaurantes/*"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostResponseMessages())
                .globalResponses(HttpMethod.PATCH, globalPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(GenericProblem.class), typeResolver.resolve(CustomProblem.class))
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(buildPageTypeRole(PedidoSingletonDTO.class))
                .alternateTypeRules(buildPageTypeRole(UsuarioDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(CidadeDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(CozinhaDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(EstadoDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(EstatisticaDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(FormaPagamentoDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(FotoProduto.class))
                .alternateTypeRules(buildCollectionModelTypeRole(GrupoDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(ItemPedidoDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(PedidoDTO.class))
                .alternateTypeRules(buildCollectionModelPagedTypeRole(PedidoDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(PermissaoDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(ProdutoDTO.class))
                .alternateTypeRules(buildCollectionModelTypeRole(RestauranteDTO.class))
                .alternateTypeRules(buildCollectionModelPagedTypeRole(UsuarioDTO.class))
                .ignoredParameterTypes(ServletWebRequest.class)
                .tags(
                        new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Estatísticas", "Gerencia as estatísticas e relatórios"),
                        new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Grupos", "Gerencia os grupos"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Usuários", "Gerencia os usuários"),
                        new Tag("Root Entry Point", "Entrada da API")
                )
                .securitySchemes(List.of(securityScheme())) //descreve o protocolo de segurança
                .apiInfo(apiInfo());
    }

    private <T> AlternateTypeRule buildPageTypeRole(Class<T> classModel) {

        return AlternateTypeRules.newRule(
                typeResolver.resolve(PagedModel.class, classModel),
                typeResolver.resolve(PageModelOpenApi.class, classModel)
        );
    }

    private <T> AlternateTypeRule buildCollectionModelTypeRole(Class<T> classModel) {

        return AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel.class, classModel),
                typeResolver.resolve(CollectionModelOpenApi.class, classModel)
        );
    }

    private <T> AlternateTypeRule buildCollectionModelPagedTypeRole(Class<T> classModel) {

        return AlternateTypeRules.newRule(
                typeResolver.resolve(PagedModel.class, classModel),
                typeResolver.resolve(CollectionModelPagedOpenApi.class, classModel)
        );
    }

    private Consumer<RepresentationBuilder> getCustomProblemModelReference() {
        return r -> r.model(m -> m.name("CustomProblem")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                        q -> q.name("CustomProblem").namespace("com.algaworks.algafood.api.v1.exceptionhandler")))));
    }

    private Consumer<RepresentationBuilder> getGenericProblemModelReference() {
        return r -> r.model(m -> m.name("GenericProblem")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                        q -> q.name("GenericProblem").namespace("com.algaworks.algafood.api.v1.exceptionhandler")))));
    }

    private List<Response> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getGenericProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .description("Recurso não encontrado")
                        .build()
        );
    }

    private List<Response> globalPostResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getGenericProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getCustomProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getGenericProblemModelReference())
                        .build()
        );
    }

    private List<Response> globalPutResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getGenericProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getCustomProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getGenericProblemModelReference())
                        .build()
        );
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getGenericProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getCustomProblemModelReference())
                        .build()
        );
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Algafood API")
                .description("Api aberta para clientes e restaurantes da Aplicação Algafood")
                .contact(new Contact("Giovanna Albuquerque", "https://github.com/GHBAlbuquerque",
                        "ghb.albuquerque@gmail.com"))
                .build();
    }

    private SecurityScheme securityScheme() {
        var securityScheme= OAuth2Scheme.OAUTH2_PASSWORD_FLOW_BUILDER
                .name("AlgaFood")
                .tokenUrl("/oauth/token")
                .scopes(List.of(new AuthorizationScope("write", "permite escrita"),
                                new AuthorizationScope("read", "permite leitura")))
                .build();

        return securityScheme;
    }
}
