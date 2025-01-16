package com.jhcs.wavechat.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Classe base para entidades com auditoria.
 * Inclui campos para data de criação e última modificação.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditingEntity {

    /**
     * Data de criação da entidade.
     * Preenchido automaticamente pelo sistema.
     */
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    /**
     * Data da última modificação da entidade.
     * Preenchido automaticamente pelo sistema.
     */
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false, updatable = false)
    private LocalDateTime lastModifiedDate;
}