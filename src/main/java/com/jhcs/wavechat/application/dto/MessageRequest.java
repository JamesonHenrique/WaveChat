package com.jhcs.wavechat.application.dto;

import com.jhcs.wavechat.domain.enums.MessageType;
import lombok.Builder;

@Builder
public record MessageRequest(
        String content,
        String senderId,
        String receiverId,
        MessageType type,
        String chatId
) {}
