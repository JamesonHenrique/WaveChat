package com.jhcs.wavechat.infrastructure.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Conversor que transforma um token JWT em uma instância de AbstractAuthenticationToken.
 */
public class KeycloakJwtAuthenticationConverter
        implements Converter<Jwt, AbstractAuthenticationToken> {

    /**
     * Converte um token JWT em uma instância de JwtAuthenticationToken.
     *
     * @param jwt O token JWT a ser convertido.
     * @return Uma instância de JwtAuthenticationToken contendo as autoridades extraídas do token.
     */
    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {

        return new JwtAuthenticationToken(jwt, Stream.concat(new JwtGrantedAuthoritiesConverter().convert(jwt)
                        .stream(), extractResourceRoles(jwt).stream())
                .collect(toSet()));
    }

    /**
     * Extrai as roles de recursos de um token JWT.
     *
     * @param jwt O token JWT contendo as roles de recursos.
     * @return Uma coleção de GrantedAuthority representando as roles extraídas.
     */

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        var resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));

        var eternal = (Map<String, List<String>>) resourceAccess.get("account");

        var roles = eternal.get("roles");

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
                .collect(toSet());
    }
}