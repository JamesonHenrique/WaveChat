package com.jhcs.wavechat.application.service;

import com.jhcs.wavechat.infrastructure.adapter.mapper.ChatMapper;
import com.jhcs.wavechat.application.dto.ChatResponse;
import com.jhcs.wavechat.domain.entity.Chat;
import com.jhcs.wavechat.domain.repository.ChatRepository;
import com.jhcs.wavechat.domain.entity.User;
import com.jhcs.wavechat.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final UserRepository userRepository;

    /**
     * Obtém os chats pelo ID do receptor.
     *
     * Este método recupera todos os chats associados ao usuário autenticado atual,
     * identificando-o pelo seu ID. Ele retorna uma lista de objetos ChatResponse
     * que representam os chats encontrados.
     *
     * @param currentUser O usuário atualmente autenticado.
     * @return Uma lista de respostas de chats.
     */
    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser) {
        final String userId = currentUser.getName();
        return chatRepository.findChatsBySenderId(userId)
                .stream()
                .map(c -> chatMapper.toChatResponse(c, userId))
                .toList();
    }

    /**
     * Cria um novo chat entre o remetente e o receptor.
     *
     * Este método cria um novo chat entre dois usuários, identificados pelos seus
     * IDs. Se um chat já existir entre os dois usuários, o ID do chat existente é
     * retornado. Caso contrário, um novo chat é criado e salvo no repositório, e o
     * ID do novo chat é retornado.
     *
     * @param senderId O ID do remetente.
     * @param receiverId O ID do receptor.
     * @return O ID do chat criado.
     * @throws EntityNotFoundException Se o remetente ou o receptor não forem encontrados.
     */
    public String createChat(String senderId, String receiverId) {
        Optional<Chat> existingChat = chatRepository.findChatBySenderAndReceiver(senderId, receiverId)
                .stream()
                .findFirst();
        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }
        User sender = userRepository.findByPublicId(senderId)
                .orElseThrow(() -> new EntityNotFoundException("User com id " + senderId + " não encontrado"));
        User receiver = userRepository.findByPublicId(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("User com id " + receiverId + " não encontrado"));
        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(receiver);
        Chat chatSaved = chatRepository.save(chat);
        return chatSaved.getId();
    }
}