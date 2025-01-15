package com.jhcs.wavechat.message;

import com.jhcs.wavechat.file.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
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
