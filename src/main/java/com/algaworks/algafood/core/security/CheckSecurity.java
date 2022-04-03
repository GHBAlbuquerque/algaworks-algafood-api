package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//classe de meta anotações de segurança
public @interface CheckSecurity {

    public @interface Cozinhas {

        @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }
    }

    public @interface Restaurantes {

        @PreAuthorize("hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarCadastro {
        }

        @PreAuthorize("(hasAuthority('EDITAR_RESTAURANTES') or @algaSecurity.gerenciaRestaurante(#restauranteId))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarFuncionamento {
        }

        @PreAuthorize("isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

    }

    public @interface Pedidos {

        @PreAuthorize("isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeListar {
        }

        @PreAuthorize("isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or " //executado depois da busca ser feita, antes do resultado ser deserializado
                + "@algaSecurity.getUsuarioId() == returnObject.cliente.id or " //posso chamar o objeto retornado via returnObject
                + "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeBuscar {
        }

        @PreAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@algaSecurity.getUsuarioId() == #filter.clienteId or"
                + "@algaSecurity.gerenciaRestaurante(#filter.restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodePesquisar {
        }

        @PreAuthorize("hasAuthority('GERENCIAR_PEDIDOS') or "
                + "@algaSecurity.gerenciaRestauranteDoPedido(#codigoPedido)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarStatus {
        }

        @PreAuthorize("isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeCriar {
        }

    }

    public @interface FormasPagamento {

        @PreAuthorize("hasAuthority('CONSULTAR_FORMAS_PAGAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

        @PreAuthorize("hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }
    }

    public @interface Cidades {
        @PreAuthorize("hasAuthority('CONSULTAR_CIDADES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

        @PreAuthorize("hasAuthority('EDITAR_CIDADES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }
    }

    public @interface Estados {
        @PreAuthorize("hasAuthority('CONSULTAR_ESTADOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

        @PreAuthorize("hasAuthority('EDITAR_ESTADOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }
    }

    public @interface UsuariosGruposPermissoes {

        @PreAuthorize("hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

        @PreAuthorize("hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("isAuthenticated() and @algaSecurity.getUsuarioId() == #id")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeAlterarPropriaSenha {
        }

        @PreAuthorize("(isAuthenticated() and @algaSecurity.getUsuarioId() == #id)" +
                "or hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeAlterarUsuario {
        }

    }

}