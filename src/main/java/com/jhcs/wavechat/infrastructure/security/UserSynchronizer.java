package com.jhcs.wavechat.infrastructure.security;

import com.jhcs.wavechat.domain.entity.User;
import com.jhcs.wavechat.domain.repository.UserRepository;
import com.jhcs.wavechat.infrastructure.adapter.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Serviço responsável por sincronizar o usuário com o IdP (Identity Provider).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Sincroniza o usuário com o IdP usando o token JWT fornecido.
     *
     * @param token O token JWT contendo as informações do usuário.
     */
    public void synchronizeWithIdp(Jwt token) {
        log.info("Sincronizando usuário com IDP");
        getUserEmail(token).ifPresent(userEmail -> {
            log.info("Sincronizando usuário com email: {}", userEmail);
            Optional<User> optUser = userRepository.findByEmail(userEmail);
            User user = userMapper.fromTokenAttributes(token.getClaims());
            optUser.ifPresent(value -> user.setId(value.getId()));
            userRepository.save(user);
        });
    }

    /**
     * Obtém o email do usuário a partir do token JWT.
     *
     * @param token O token JWT contendo as informações do usuário.
     * @return Um Optional contendo o email do usuário, se presente.
     */
    private Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> attributes = token.getClaims();
        if (attributes.containsKey("email")) {
            return Optional.of((String) attributes.get("email").toString());
        }
        return Optional.empty();
    }
}