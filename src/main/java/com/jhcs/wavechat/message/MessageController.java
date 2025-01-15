package com.jhcs.wavechat.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/messages")
public class MessageController {
    private final MessageService messageService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMessage(@RequestBody  MessageRequest messageRequest) {
        messageService.saveMessage(messageRequest);
    }
    @PostMapping(value = "/upload-media",consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMedia(@RequestParam("chat-id") String chatId, @RequestParam("media") MultipartFile file, Authentication authentication)  {
        messageService.uploadMediaMessage(chatId, file,authentication);
    }
    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void setMessageService(@RequestParam("chat-id") String chatId, Authentication authentication) {
        messageService.setMessageToSeen(chatId, authentication);
    }
    @GetMapping("/chat/{chat-id}")
    public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable("chat-id") String chatId, Authentication authentication) {

        return ResponseEntity.ok(messageService.findChatMessages(chatId));
    }

}
