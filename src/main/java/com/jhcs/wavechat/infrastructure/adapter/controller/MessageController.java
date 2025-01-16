package com.jhcs.wavechat.infrastructure.adapter.controller;

import com.jhcs.wavechat.application.service.MessageService;
import com.jhcs.wavechat.application.dto.MessageRequest;
import com.jhcs.wavechat.application.dto.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/messages")
@Tag(name = "Mensagem", description = "Operações relacionadas a mensagens")
public class MessageController {
    private final MessageService messageService;

    /**
     * Salva uma nova mensagem.
     *
     * @param messageRequest O objeto MessageRequest contendo os dados da mensagem.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Salvar mensagem", description = "Salva uma nova mensagem")
    public void saveMessage(@RequestBody MessageRequest messageRequest) {
        messageService.saveMessage(messageRequest);
    }

    /**
     * Envia um arquivo de mídia em uma mensagem.
     *
     * @param chatId O ID do chat.
     * @param file O arquivo de mídia a ser enviado.
     * @param authentication O objeto de autenticação do usuário.
     */
    @PostMapping(value = "/upload-media", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Enviar mídia", description = "Envia um arquivo de mídia em uma mensagem")
    public void uploadMedia(@RequestParam("chat-id") String chatId, @RequestParam("media") @Parameter(description = "Arquivo de mídia") MultipartFile file, Authentication authentication) {
        messageService.uploadMediaMessage(chatId, file, authentication);
    }

    /**
     * Marca as mensagens de um chat como vistas.
     *
     * @param chatId O ID do chat.
     * @param authentication O objeto de autenticação do usuário.
     */
    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Marcar mensagem como vista", description = "Marca as mensagens de um chat como vistas")
    public void setMessageService(@RequestParam("chat-id") String chatId, Authentication authentication) {
        messageService.setMessageToSeen(chatId, authentication);
    }

    /**
     * Obtém todas as mensagens de um chat específico.
     *
     * @param chatId O ID do chat.
     * @param authentication O objeto de autenticação do usuário.
     * @return Uma ResponseEntity contendo a lista de MessageResponse.
     */
    @GetMapping("/chat/{chat-id}")
    @Operation(summary = "Obter mensagens do chat", description = "Obtém todas as mensagens de um chat específico")
    public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable("chat-id") String chatId, Authentication authentication) {
        return ResponseEntity.ok(messageService.findChatMessages(chatId));
    }
}