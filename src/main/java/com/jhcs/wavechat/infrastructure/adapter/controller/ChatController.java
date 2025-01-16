package com.jhcs.wavechat.infrastructure.adapter.controller;

import com.jhcs.wavechat.application.service.ChatService;
import com.jhcs.wavechat.application.dto.ChatResponse;
import com.jhcs.wavechat.domain.common.StringResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/chats")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "Operações relacionadas ao chat")
public class ChatController {
    private final ChatService chatService;

    /**
     * Cria um novo chat entre dois usuários.
     *
     * @param senderId O ID do remetente.
     * @param receiverId O ID do receptor.
     * @return Uma ResponseEntity contendo o ID do chat criado.
     */
    @PostMapping
    @Operation(summary = "Criar um novo chat", description = "Cria um novo chat entre dois usuários")
    public ResponseEntity<StringResponse> createChat(@RequestParam(name = "sender-id") String senderId, @RequestParam(name = "receiver-id") String receiverId) {
        final String chatId = chatService.createChat(senderId, receiverId);
        StringResponse response = StringResponse.builder()
                .response(chatId)
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Obtém todos os chats onde o usuário autenticado é o receptor.
     *
     * @param authentication O objeto de autenticação do usuário.
     * @return Uma ResponseEntity contendo a lista de ChatResponse.
     */
    @GetMapping
    @Operation(summary = "Obter chats por receptor", description = "Obtém todos os chats onde o usuário autenticado é o receptor")
    public ResponseEntity<List<ChatResponse>> getChatsByReceiver(Authentication authentication) {
        final List<ChatResponse> chats = chatService.getChatsByReceiverId(authentication);
        return ResponseEntity.ok(chats);
    }
}