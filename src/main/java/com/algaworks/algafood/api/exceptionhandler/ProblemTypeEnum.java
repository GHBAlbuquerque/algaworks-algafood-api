package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemTypeEnum {
	
	ENTIDADE_NAO_ENCONTRADA("Entidade náo encontrada", "/entidade-nao-encontrada"),
	ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
	ERRO_DE_NEGOCICO("Erro de negocio", "/erro-de-negocio"),
	MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensivel", "/mensagem-incompreensivel"),
	PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido");
	
	private final String title;
	private final String uri;
	
	
	private ProblemTypeEnum(String title, String path) {
		this.title = title;
		this.uri = String.format("https://algafood.com.br%s", path);
	}
	
	
}
