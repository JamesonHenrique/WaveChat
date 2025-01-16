package com.jhcs.wavechat.chat;


import com.jhcs.wavechat.common.StringResponse;
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

    @PostMapping
    @Operation(summary = "Criar um novo chat", description = "Cria um novo chat entre dois usuários")
    public ResponseEntity<StringResponse> createChat(@RequestParam(name = "sender-id") String senderId, @RequestParam(name = "receiver-id") String receiverId) {
        final String chatId = chatService.createChat(senderId, receiverId);
        StringResponse response = StringResponse.builder()
                .response(chatId)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Obter chats por receptor", description = "Obtém todos os chats onde o usuário autenticado é o receptor")
    public ResponseEntity<List<ChatResponse>> getChatsByReceiver(Authentication authentication) {
        final List<ChatResponse> chats = chatService.getChatsByReceiverId(authentication);
        return ResponseEntity.ok(chats);
    }
}