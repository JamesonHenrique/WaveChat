package com.jhcs.wavechat.infrastructure.adapter.mapper;

import com.jhcs.wavechat.domain.entity.User;
import com.jhcs.wavechat.application.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserMapper {

    /**
     * Converte atributos de token em um objeto User.
     *
     * @param attributes Um mapa contendo os atributos do token.
     * @return Um objeto User preenchido com os atributos do token.
     */
    public User fromTokenAttributes(Map<String, Object> attributes) {
        User user = new User();
        if (attributes.containsKey("sub")) {
            user.setId((String) attributes.get("sub"));
        }
        if (attributes.containsKey("given_name")) {
            user.setFirstName((String) attributes.get("given_name"));
        } else if (attributes.containsKey("nickname")) {
            user.setFirstName((String) attributes.get("nickname"));
        }
        if (attributes.containsKey("family_name")) {
            user.setLastName((String) attributes.get("family_name"));
        }
        if (attributes.containsKey("email")) {
            user.setEmail((String) attributes.get("email"));
        }
        user.setLastSeen(LocalDateTime.now());
        return user;
    }

    /**
     * Converte um objeto User em um objeto UserResponse.
     *
     * @param user O objeto User a ser convertido.
     * @return Um objeto UserResponse preenchido com os dados do User.
     */
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .lastSeen(user.getLastSeen())
                .isOnline(user.isUserOnline())
                .build();
    }
}