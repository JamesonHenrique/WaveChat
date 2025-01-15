package com.jhcs.wavechat.message;

import com.jhcs.wavechat.chat.Chat;
import com.jhcs.wavechat.chat.ChatRepository;
import com.jhcs.wavechat.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository
            messageRepository;
    private final ChatRepository
            chatRepository;
    private final MessageMapper
            messageMapper;
    private final FileService fileService;
    public void saveMessage(MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.chatId()).orElseThrow(() -> new EntityNotFoundException("Chat com id " + messageRequest.chatId() + " não encontrado"));
        Message message = new Message();
        message.setContent(messageRequest.content());
        message.setChat(chat);
        message.setState(MessageState.SENT);
        message.setType(messageRequest.type());
        message.setSenderId(messageRequest.senderId());
        message.setReceiverId(messageRequest.receiverId());
        messageRepository.save(message);
    }
    public List<MessageResponse> findChatMessages(String chatId) {
        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(messageMapper::toMessageResponse)
                .toList();
    }
    @Transactional
    public void setMessageToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat with id " + chatId + " not found"));
       final String recipientId= getRecipientId(chat,authentication);
       messageRepository.setMessagesToSeenByChat(chatId, MessageState.SEEN);

    }

    private String getRecipientId(
            Chat chat,
            Authentication authentication
                                  ) {
        if (chat.getRecipient().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        }
       return chat.getSender().getId();
    }
    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat com id " + chatId + " não encontrado"));
        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);
        final String filePath  = fileService.saveFile(file,senderId);
        Message message = new Message();
        message.setChat(chat);
        message.setState(MessageState.SENT);
        message.setType(MessageType.IMAGE);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setMediaFilePath(filePath);
        ;
        messageRepository.save(message);
    }

    private String getSenderId(
            Chat chat,
            Authentication authentication
                               ) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }
}
