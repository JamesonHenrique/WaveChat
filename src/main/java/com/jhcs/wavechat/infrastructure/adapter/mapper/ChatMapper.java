package com.jhcs.wavechat.infrastructure.adapter.mapper;

import com.jhcs.wavechat.application.dto.ChatResponse;
import com.jhcs.wavechat.domain.entity.Chat;
import org.springframework.stereotype.Service;

@Service
public class ChatMapper {

    /**
     * Converte um objeto Chat em um objeto ChatResponse.
     *
     * @param chat O objeto Chat a ser convertido.
     * @param senderId O ID do remetente.
     * @return Um objeto ChatResponse preenchido com os dados do Chat.
     */
    public ChatResponse toChatResponse(
            Chat chat,
            String senderId
                                      ) {
        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(senderId))
                .unreadCount(chat.getUnreadMessage(senderId))
                .lastMessage(chat.getLastMessage())
                .isRecipientOnline(chat.getRecipient()
                        .isUserOnline())
                .receiverId(chat.getRecipient()
                        .getId())
                .senderId(chat.getSender().getId())

                .build();

    }
}