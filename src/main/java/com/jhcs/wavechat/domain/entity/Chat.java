package com.jhcs.wavechat.domain.entity;

import com.jhcs.wavechat.domain.constants.ChatConstants;
import com.jhcs.wavechat.domain.common.BaseAuditingEntity;
import com.jhcs.wavechat.domain.enums.MessageState;
import com.jhcs.wavechat.domain.enums.MessageType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade que representa um chat no sistema.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID, query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :senderId OR c.recipient.id = :senderId ORDER BY c.createdDate DESC")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER, query = "SELECT DISTINCT c FROM Chat c WHERE (c.sender.id = :senderId AND c.recipient.id = :recipientId) OR (c.sender.id = :recipientId AND c.recipient.id = :senderId)")
@Tag(name = "Chat")
public class Chat extends BaseAuditingEntity {

    /**
     * Identificador único do chat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Usuário que enviou a mensagem.
     */
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    /**
     * Usuário que recebeu a mensagem.
     */
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    /**
     * Lista de mensagens associadas ao chat.
     */
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate DESC")
    private List<Message> messages;

    /**
     * Obtém o nome do chat com base no ID do remetente.
     *
     * @param senderId O ID do remetente.
     * @return O nome do chat.
     */
    @Transient
    public String getChatName(String senderId) {
        if (recipient.getId().equals(senderId)) {
            return sender.getFirstName() + " " + sender.getLastName();
        }
        return recipient.getFirstName() + " " + recipient.getLastName();
    }
    @Transient
    public String getTargetChatName(String senderId) {
        if (sender.getId().equals(senderId)) {
            return sender.getFirstName() + " " + sender.getLastName();
        }
        return recipient.getFirstName() + " " + recipient.getLastName();
    }
    /**
     * Obtém a contagem de mensagens não lidas para um remetente específico.
     *
     * @param senderId O ID do remetente.
     * @return A contagem de mensagens não lidas.
     */
    @Transient
    public long getUnreadMessage(final String senderId) {
        return this.messages.stream()
                .filter(m -> m.getReceiverId().equals(senderId))
                .filter(m -> MessageState.SENT == m.getState())
                .count();
    }

    /**
     * Obtém o conteúdo da última mensagem do chat.
     *
     * @return O conteúdo da última mensagem ou "Anexo" se não for uma mensagem de texto.
     */
    @Transient
    public String getLastMessage() {
        if (messages != null && !messages.isEmpty()) {
            if (messages.get(0).getType() != MessageType.TEXT) {
                return "Anexo";
            }
            return messages.get(0).getContent();
        }
        return null;
    }

    /**
     * Obtém a data e hora da última mensagem do chat.
     *
     * @return A data e hora da última mensagem ou null se não houver mensagens.
     */
    @Transient
    public LocalDateTime getLastMessageTime() {
        if (messages != null && !messages.isEmpty()) {
            return messages.get(0).getCreatedDate();
        }
        return null;
    }
}