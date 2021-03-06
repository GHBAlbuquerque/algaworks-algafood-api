package com.algaworks.algafood.api.v1.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemTypeEnum {

    RECURSO_NAO_ENCONTRADO("Recurso náo encontrado", "/recurso-nao-encontrado"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
    ERRO_DE_NEGOCICO("Erro de negocio", "/erro-de-negocio"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensivel", "/mensagem-incompreensivel"),
    PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido"),
    ERRO_DE_SISTEMA("Erro de Sistema", "/erro-de-sistema"),
    DADOS_INVALIDOS("Dados Inválidos", "/dados-invalidos"),
    ERRO_AO_VALIDAR("Erro ao validar", "/erro-ao-validar"),
    ERRO_AO_CONVERTER("Erro ao converter", "/erro-ao-converter"),
    ERRO_AO_MANIPULAR_ARQUIVO("Erro ao manipular arquivo", "/erro-ao-manipular-arquivo"),
    ERRO_DE_EMAIL("Erro de e-mail", "/erro-de-email"),
    ACESSO_NEGADO("/acesso-negado", "Acesso negado");

    private final String title;
    private final String uri;

    private ProblemTypeEnum(String title, String path) {
        this.title = title;
        this.uri = String.format("https://algafood.com.br%s", path);
    }


}
