package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (authentication.getPrincipal() instanceof AuthUser) {
            AuthUser user = (AuthUser) authentication.getPrincipal();

            Map<String, Object> info = new HashMap<>();
            info.put("nome_completo", user.getFullName());
            info.put("usuario_id", user.getId());

            DefaultOAuth2AccessToken defaultToken = (DefaultOAuth2AccessToken) accessToken;
            defaultToken.setAdditionalInformation(info);
        }

        return accessToken;
    }
}
