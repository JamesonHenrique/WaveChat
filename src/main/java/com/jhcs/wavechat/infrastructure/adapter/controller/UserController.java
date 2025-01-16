package com.jhcs.wavechat.infrastructure.adapter.controller;

import com.jhcs.wavechat.application.service.UserService;
import com.jhcs.wavechat.application.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Operações relacionadas aos usuários")
public class UserController {
    private final UserService userService;

    /**
     * Obtém todos os usuários, exceto o usuário autenticado.
     *
     * @param authentication O objeto de autenticação do usuário.
     * @return Uma ResponseEntity contendo a lista de UserResponse.
     */
    @GetMapping
    @Operation(summary = "Obter todos os usuários", description = "Obtém todos os usuários, exceto o usuário autenticado")
    public ResponseEntity<List<UserResponse>> getAllUsers(Authentication authentication) {
        return ResponseEntity.ok(userService.getAllUsersExceptSelf(authentication));
    }
}