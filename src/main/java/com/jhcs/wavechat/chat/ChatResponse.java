package com.jhcs.wavechat.chat;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record ChatResponse(
        String id, String name,
        long unreadCount, String lastMessage, LocalDateTime lastMessageTime,
        boolean isRecipientOnline, String senderId, String receiverId) {}
