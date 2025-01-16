package com.jhcs.wavechat.domain.repository;

import com.jhcs.wavechat.domain.entity.Message;
import com.jhcs.wavechat.domain.constants.MessageConstants;
import com.jhcs.wavechat.domain.enums.MessageState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Encontra todas as mensagens pelo ID do chat.
     *
     * @param chatId O ID do chat.
     * @return Uma lista de objetos Message.
     */
    @Query(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID)
    List<Message> findMessagesByChatId(@Param("chatId") String chatId);

    /**
     * Define o estado das mensagens de um chat como visto.
     *
     * @param chatId O ID do chat.
     * @param newState O novo estado das mensagens.
     */
    @Query(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT)
    @Modifying
    void setMessagesToSeenByChat(@Param("chatId") String chatId, @Param("newState") MessageState newState);
}