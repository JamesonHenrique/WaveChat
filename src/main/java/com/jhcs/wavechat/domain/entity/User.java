package com.jhcs.wavechat.domain.entity;

import com.jhcs.wavechat.domain.common.BaseAuditingEntity;
import com.jhcs.wavechat.domain.constants.UserConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade que representa um usuário no sistema.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = UserConstants.FIND_USER_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email")
@NamedQuery(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF, query = "SELECT u FROM User u WHERE u.id != :publicId")
@NamedQuery(name = UserConstants.FIND_USER_BY_PUBLIC_ID, query = "SELECT u FROM User u WHERE u.id = :publicId")
public class User extends BaseAuditingEntity {
    private static final int LAST_ACTIVATE_INTERVAL = 1;

    /**
     * Identificador único do usuário.
     */
    @Id
    private String id;

    /**
     * Primeiro nome do usuário.
     */
    private String firstName;

    /**
     * Sobrenome do usuário.
     */
    private String lastName;

    /**
     * Email do usuário.
     */
    private String email;

    /**
     * Data e hora da última vez que o usuário foi visto online.
     */
    private LocalDateTime lastSeen;

    /**
     * Lista de chats onde o usuário é o remetente.
     */
    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender;

    /**
     * Lista de chats onde o usuário é o receptor.
     */
    @OneToMany(mappedBy = "recipient")
    private List<Chat> chatsAsReceiver;

    /**
     * Verifica se o usuário está online.
     *
     * @return true se o usuário estiver online, caso contrário false.
     */
    @Transient
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now()
                .minusMinutes(LAST_ACTIVATE_INTERVAL));
    }
}