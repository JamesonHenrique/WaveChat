package com.jhcs.wavechat.chat;

import com.jhcs.wavechat.user.User;
import com.jhcs.wavechat.user.UserRepository;
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

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser) {
        final String userId = currentUser.getName(); return chatRepository.findChatsBySenderId(userId)
                .stream()
                .map(c -> chatMapper.toChatResponse(c, userId))
                .toList();
    }
    public String createChat(String senderId, String receiverId) {
        Optional<Chat>existingChat = chatRepository.findChatBySenderAndReceiver(senderId, receiverId)
                .stream()
                .findFirst();
        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }
        User sender = userRepository.findByPublicId(senderId)
                .orElseThrow(() -> new EntityNotFoundException("User com id" + senderId + " não encontrado"));
        User receiver = userRepository.findByPublicId(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("User com id" + receiverId + " não encontrado"));
        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(receiver);
       Chat chatSaved = chatRepository.save(chat);
        return chatSaved.getId();
    }
}
