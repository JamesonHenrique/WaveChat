package com.jhcs.wavechat.infrastructure.notification;

import com.jhcs.wavechat.domain.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que representa uma notificação.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    /**
     * ID do chat.
     */
    private String chatId;

    /**
     * Conteúdo da notificação.
     */
    private String content;

    /**
     * ID do remetente.
     */
    private String senderId;

    /**
     * ID do destinatário.
     */
    private String receiverId;

    /**
     * Nome do chat.
     */
    private String chatName;

    /**
     * Tipo de mensagem.
     */
    private MessageType messageType;

    /**
     * Tipo de notificação.
     */
    private NotificationType type;

    /**
     * Mídia associada à notificação.
     */
    private byte[] media;
}