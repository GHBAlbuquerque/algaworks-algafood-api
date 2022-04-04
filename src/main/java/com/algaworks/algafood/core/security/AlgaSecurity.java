package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecurity {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        var jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("usuario_id");
    }

    public boolean gerenciaRestaurante(Long RestauranteId) {
        var ehResponsavel = restauranteRepository.existeResponsavel(RestauranteId, getUsuarioId());
        return ehResponsavel;
    }

    public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
        return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities()
                .stream()
                .anyMatch(authority -> authority
                        .getAuthority()
                        .equals(authorityName));
    }

    public boolean podeGerenciarPedidos(String codigoPedido) {
        return hasAuthority("GERENCIAR_PEDIDOS") || gerenciaRestauranteDoPedido(codigoPedido);
    }


    public boolean podeGerenciarRestaurante(Long restauranteId) {
        return hasAuthority("GERENCIAR_PEDIDOS") || gerenciaRestaurante(restauranteId);
    }

}
