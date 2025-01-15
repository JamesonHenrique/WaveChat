package com.jhcs.wavechat.message;

import lombok.Builder;

@Builder
public record MessageRequest(
        String content,
        String senderId,
        String receiverId,
        MessageType type,
        String chatId
) {}
