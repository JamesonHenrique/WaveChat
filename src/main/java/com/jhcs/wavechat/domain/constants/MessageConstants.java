package com.jhcs.wavechat.domain.constants;

/**
 * Classe que contém constantes relacionadas às mensagens.
 */
public class MessageConstants {
    /**
     * Consulta para encontrar mensagens pelo ID do chat.
     */
    public static final String FIND_MESSAGES_BY_CHAT_ID =
            "Messages.findMessagesByChatId";

    /**
     * Consulta para definir mensagens como vistas pelo chat.
     */
    public static final String SET_MESSAGES_TO_SEEN_BY_CHAT =
            "Messages.setMessagesToSeenByChat";

    /**
     * Construtor padrão.
     */
    public MessageConstants() {
    }
}