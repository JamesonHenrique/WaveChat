package com.jhcs.wavechat.domain.constants;

/**
 * Classe que contém constantes relacionadas aos chats.
 */
public class ChatConstants {
    /**
     * Consulta para encontrar chats pelo ID do remetente.
     */
    public static final String FIND_CHAT_BY_SENDER_ID = "Chat.findChatBySenderId";

    /**
     * Consulta para encontrar chats pelo ID do remetente e do receptor.
     */
    public static final String FIND_CHAT_BY_SENDER_ID_AND_RECEIVER = "Chat.findChatBySenderIdAndReceiver";

    /**
     * Construtor padrão.
     */
    public ChatConstants() {
    }
}