package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

//classe de meta anotações de segurança
public @interface CheckSecurity {

    public @interface Restaurantes {


        @PreAuthorize("hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarCadastro { }

        @PreAuthorize("(hasAuthority('EDITAR_RESTAURANTES') or @algaSecurity.gerenciaRestaurante(#restauranteId))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarFuncionamento { }

        @PreAuthorize("isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar { }

    }

    public @interface Pedidos {

        @PreAuthorize("isAuthenticated() and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or " //executado depois da busca ser feita, antes do resultado ser deserializado
                + "@algaSecurity.getUsuarioId() == returnObject.cliente.id or " //posso chamar o objeto retornado via returnObject
                + "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeBuscar { }

    }

}