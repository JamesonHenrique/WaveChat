package com.jhcs.wavechat.application.service;

import com.jhcs.wavechat.application.dto.MessageRequest;
import com.jhcs.wavechat.application.dto.MessageResponse;
import com.jhcs.wavechat.domain.entity.Chat;
import com.jhcs.wavechat.domain.entity.Message;
import com.jhcs.wavechat.domain.enums.MessageState;
import com.jhcs.wavechat.domain.enums.MessageType;
import com.jhcs.wavechat.domain.repository.ChatRepository;
import com.jhcs.wavechat.domain.repository.MessageRepository;
import com.jhcs.wavechat.infrastructure.util.FileUtils;
import com.jhcs.wavechat.infrastructure.adapter.mapper.MessageMapper;
import com.jhcs.wavechat.infrastructure.notification.Notification;
import com.jhcs.wavechat.infrastructure.notification.NotificationService;
import com.jhcs.wavechat.infrastructure.notification.NotificationType;
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
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final FileService fileService;
    private final NotificationService notificationService;

    /**
     * Salva uma mensagem no repositório.
     *
     * @param messageRequest O objeto de solicitação contendo os detalhes da mensagem.
     */
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
        Notification notification = Notification
                .builder()
                .chatId(chat.getId())
                .senderId(messageRequest.senderId())
                .receiverId(messageRequest.receiverId())
                .type(NotificationType.MESSAGE)
                .chatName(chat.getChatName(messageRequest.senderId()))
                .content(messageRequest.content())
                .build();
        notificationService.sendNotification(message.getReceiverId(), notification);
    }

    /**
     * Encontra todas as mensagens de um chat específico.
     *
     * @param chatId O ID do chat.
     * @return Uma lista de respostas de mensagens.
     */
    public List<MessageResponse> findChatMessages(String chatId) {
        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(messageMapper::toMessageResponse)
                .toList();
    }

    /**
     * Define o estado das mensagens de um chat como visto.
     *
     * @param chatId O ID do chat.
     * @param authentication A autenticação do usuário.
     */
    @Transactional
    public void setMessageToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat com id " + chatId + " não encontrado"));
        final String recipientId = getRecipientId(chat, authentication);
        messageRepository.setMessagesToSeenByChat(chatId, MessageState.SEEN);
        Notification notification = Notification
                .builder()
                .chatId(chat.getId())
                .type(NotificationType.SEEN)
                .senderId(getSenderId(chat, authentication))
                .receiverId(recipientId)
                .build();
        notificationService.sendNotification(recipientId, notification);
    }

    /**
     * Obtém o ID do destinatário de um chat.
     *
     * @param chat O objeto do chat.
     * @param authentication A autenticação do usuário.
     * @return O ID do destinatário.
     */
    private String getRecipientId(Chat chat, Authentication authentication) {
        if (chat.getRecipient().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }

    /**
     * Faz o upload de uma mensagem de mídia.
     *
     * @param chatId O ID do chat.
     * @param file O arquivo de mídia.
     * @param authentication A autenticação do usuário.
     */
    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat com id " + chatId + " não encontrado"));
        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);
        final String filePath = fileService.saveFile(file, senderId);
        Message message = new Message();
        message.setChat(chat);
        message.setState(MessageState.SENT);
        message.setType(MessageType.IMAGE);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setMediaFilePath(filePath);
        messageRepository.save(message);
        Notification notification = Notification
                .builder()
                .chatId(chat.getId())
                .type(NotificationType.IMAGE)
                .senderId(senderId)
                .receiverId(recipientId)
                .messageType(MessageType.IMAGE)
                .media(FileUtils.readFileFromLocation(filePath))
                .build();
        notificationService.sendNotification(recipientId, notification);
    }

    /**
     * Obtém o ID do remetente de um chat.
     *
     * @param chat O objeto do chat.
     * @param authentication A autenticação do usuário.
     * @return O ID do remetente.
     */
    private String getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }
}