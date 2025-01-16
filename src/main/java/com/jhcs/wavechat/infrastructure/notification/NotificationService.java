package com.jhcs.wavechat.infrastructure.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por enviar notificações via WebSocket.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Envia uma notificação para um usuário específico via WebSocket.
     *
     * @param userId O ID do usuário que receberá a notificação.
     * @param notification A notificação a ser enviada.
     */
    public void sendNotification(String userId, Notification notification) {
        log.info("Enviando notificação do WebSocket para {} com payload {}", userId, notification);
        messagingTemplate.convertAndSendToUser(userId, "/chat", notification);
    }
}