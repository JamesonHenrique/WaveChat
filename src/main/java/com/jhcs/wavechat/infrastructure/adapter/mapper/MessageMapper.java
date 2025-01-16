package com.jhcs.wavechat.infrastructure.adapter.mapper;

import com.jhcs.wavechat.application.dto.MessageResponse;
import com.jhcs.wavechat.domain.entity.Message;
import com.jhcs.wavechat.infrastructure.util.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {

    /**
     * Converte um objeto Message em um objeto MessageResponse.
     *
     * @param message O objeto Message a ser convertido.
     * @return Um objeto MessageResponse preenchido com os dados do Message.
     */
    public MessageResponse toMessageResponse(Message message) {
       return MessageResponse
                .builder()
                .id(message.getId())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .state(message.getState())
                .type(message.getType())
                .content(message.getContent())
                .createdAt(message.getCreatedDate())
                .media(FileUtils.readFileFromLocation(message.getMediaFilePath()))
                .build();
    }
}