package com.jhcs.wavechat.application.dto;

import com.jhcs.wavechat.domain.enums.MessageState;
import com.jhcs.wavechat.domain.enums.MessageType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record
MessageResponse(
        Long id,
        String content,
        MessageType type,
        MessageState state,
         String senderId,
        String receiverId,
        LocalDateTime createdAt,
        byte[] media
) {}
