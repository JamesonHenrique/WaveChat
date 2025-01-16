package com.jhcs.wavechat.application.service;

import com.jhcs.wavechat.infrastructure.adapter.mapper.UserMapper;
import com.jhcs.wavechat.domain.repository.UserRepository;
import com.jhcs.wavechat.application.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Obtém todos os usuários, exceto o usuário conectado.
     *
     * @param connectedUser O usuário atualmente autenticado.
     * @return Uma lista de respostas de usuários.
     */
    public List<UserResponse> getAllUsersExceptSelf(Authentication connectedUser) {
        return userRepository.findAllUsersExceptSelf(connectedUser.getName())
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
}