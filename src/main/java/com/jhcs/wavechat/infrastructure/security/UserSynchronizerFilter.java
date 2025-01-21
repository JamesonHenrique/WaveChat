package com.jhcs.wavechat.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que sincroniza o usuário com o IdP (Identity Provider) em cada requisição.
 */
@Component
@RequiredArgsConstructor
public class UserSynchronizerFilter extends OncePerRequestFilter {
    private final UserSynchronizer userSynchronizer;

    /**
     * Método que executa o filtro para cada requisição.
     *
     * @param request  O objeto HttpServletRequest da requisição.
     * @param response O objeto HttpServletResponse da resposta.
     * @param filterChain O objeto FilterChain para continuar a cadeia de filtros.
     * @throws ServletException Se ocorrer um erro de servlet.
     * @throws IOException Se ocorrer um erro de I/O.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
                                   ) throws ServletException, IOException {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            userSynchronizer.synchronizeWithIdp(token.getToken());
        }
        filterChain.doFilter(request, response);
    }
}