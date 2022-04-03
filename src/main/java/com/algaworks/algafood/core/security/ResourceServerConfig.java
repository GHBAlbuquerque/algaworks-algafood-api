package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, "/v1/cidades/**").hasAuthority("EDITAR_CIDADES")
                .antMatchers(HttpMethod.PUT, "/v1/cidades/**").hasAuthority("EDITAR_CIDADES")
                .antMatchers(HttpMethod.GET, "/v1/cidades/**").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
                .antMatchers(HttpMethod.PUT, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
                .antMatchers(HttpMethod.GET, "/v1/cozinhas/**").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/estados/**").hasAuthority("EDITAR_ESTADOS")
                .antMatchers(HttpMethod.PUT, "/v1/estados/**").hasAuthority("EDITAR_ESTADOS")
                .antMatchers(HttpMethod.GET, "/v1/estados/**").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/formas-pagamento/**").hasAuthority("EDITAR_FORMAS_PAGAMENTO")
                .antMatchers(HttpMethod.PUT, "/v1/formas-pagamento/**").hasAuthority("EDITAR_FORMAS_PAGAMENTO")
                .antMatchers(HttpMethod.GET, "/v1/formas-pagamento/**").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/pedidos/**").hasAuthority("GERENCIAR_PEDIDOS")
                .antMatchers(HttpMethod.PUT, "/v1/pedidos/**").hasAuthority("GERENCIAR_PEDIDOS")
                .antMatchers(HttpMethod.GET, "/v1/pedidos/**").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/permissoes/**").hasAuthority("EDITAR_USUARIOS")
                .antMatchers(HttpMethod.PUT, "/v1/permissoes/**").hasAuthority("EDITAR_USUARIOS")
                .antMatchers(HttpMethod.GET, "/v1/permissoes/**").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/restaurantes/**").hasAuthority("EDITAR_RESTAURANTES")
                .antMatchers(HttpMethod.PUT, "/v1/restaurantes/**").hasAuthority("EDITAR_RESTAURANTES")
                .antMatchers(HttpMethod.GET, "/v1/restaurantes/**").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/usuarios/**").hasAuthority("EDITAR_USUARIOS")
                .antMatchers(HttpMethod.PUT, "/v1/usuarios/**").hasAuthority("EDITAR_USUARIOS")
                .antMatchers(HttpMethod.GET, "/v1/usuarios/**").authenticated()
                .antMatchers(HttpMethod.GET, "/v1/estatisticas/**").hasAuthority("GERAR_RELATORIOS")
                .anyRequest().denyAll()
                .and()
                .cors()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**"
    };

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwt.getClaimAsStringList("authorities");

            if (authorities == null) authorities = Collections.emptyList();

            return authorities
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });

        return jwtAuthenticationConverter;
    }

}
