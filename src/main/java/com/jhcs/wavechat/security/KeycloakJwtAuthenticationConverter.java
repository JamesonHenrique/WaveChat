package com.jhcs.wavechat.security;

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

public class KeycloakJwtAuthenticationConverter
        implements Converter<Jwt,AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {

        return new JwtAuthenticationToken(
                jwt,
                Stream.concat(
                                new JwtGrantedAuthoritiesConverter().convert(jwt).stream(),
                                extractResourceRoles(jwt).stream())
                        .collect(Collectors.toSet()));


    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        var resourceAcess = new HashMap<>(jwt.getClaim("resource_access"));
        var eternal =  (Map<String, List<String>>)resourceAcess.get("account");
        var roles = eternal.get("roles");
        return roles.stream().map((role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-","_")))).collect(Collectors.toList());
    }
}
