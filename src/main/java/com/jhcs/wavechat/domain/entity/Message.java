package com.jhcs.wavechat.domain.entity;

import com.jhcs.wavechat.domain.common.BaseAuditingEntity;
import com.jhcs.wavechat.domain.constants.MessageConstants;
import com.jhcs.wavechat.domain.enums.MessageState;
import com.jhcs.wavechat.domain.enums.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade que representa uma mensagem no sistema.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
@NamedQuery(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID, query = "SELECT m FROM Message m WHERE m.chat.id = :chatId")
@NamedQuery(name=MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT, query = "UPDATE Message SET state = :newState WHERE chat.id = :chatId")
public class Message extends BaseAuditingEntity {

    /**
     * Identificador único da mensagem.
     */
    @Id
    @SequenceGenerator(name = "msg_seq", sequenceName = "msg_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_seq")
    private Long id;

    /**
     * Conteúdo da mensagem.
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * Estado da mensagem.
     */
    @Enumerated(EnumType.STRING)
    private MessageState state;

    /**
     * Tipo da mensagem.
     */
    @Enumerated(EnumType.STRING)
    private MessageType type;

    /**
     * Chat ao qual a mensagem pertence.
     */
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    /**
     * Identificador do remetente da mensagem.
     */
    @Column(name = "sender_id", nullable = false)
    private String senderId;

    /**
     * Identificador do receptor da mensagem.
     */
    @Column(name = "receiver_id", nullable = false)
    private String receiverId;

    /**
     * Caminho do arquivo de mídia associado à mensagem, se houver.
     */
    private String mediaFilePath;
}