package com.jhcs.wavechat.domain.repository;

import com.jhcs.wavechat.domain.constants.ChatConstants;
import com.jhcs.wavechat.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, String> {

    /**
     * Encontra todos os chats pelo ID do remetente.
     *
     * @param userId O ID do usuário remetente.
     * @return Uma lista de objetos Chat.
     */
    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID)
    List<Chat> findChatsBySenderId(@Param("senderId") String userId);

    /**
     * Encontra um chat pelo ID do remetente e do receptor.
     *
     * @param senderId O ID do remetente.
     * @param receiverId O ID do receptor.
     * @return Um Optional contendo o objeto Chat, se encontrado.
     */
    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER)
    Optional<Chat> findChatBySenderAndReceiver(@Param("senderId") String senderId, @Param("recipientId") String receiverId);
}