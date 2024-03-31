package com.example.intelligent.chatbot.controller;

import com.example.intelligent.chatbot.constants.ChatConstants;
import com.example.intelligent.chatbot.dto.ChatDto;
import com.example.intelligent.chatbot.entity.ChatMessage;
import com.example.intelligent.chatbot.exception.ChatProcessException;
import com.example.intelligent.chatbot.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("v1/chat")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    @PostMapping("/messages")
    @Async
    public CompletableFuture<ResponseEntity<ChatDto>> chatMessage(@RequestBody ChatDto chat) throws InterruptedException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final String messageResponse = chatService.processMessageAsync(chat).get();
                chat.setMessage(messageResponse);
            } catch (Exception exception) {
                throw new ChatProcessException(exception.getLocalizedMessage(), ChatConstants.ERROR_CODE);
            }
            return ResponseEntity.ok(chat);
        });
    }

    @GetMapping("/messages")
    @Async
    public CompletableFuture<ResponseEntity<List<ChatMessage>>> fetchAllMessages() {
        return CompletableFuture.supplyAsync(() -> {
            List<ChatMessage> messages = chatService.fetchAllMessageAsync();
            return ResponseEntity.ok(messages);

        });
    }

    @GetMapping("/messages/{userId}")
    @Async
    public CompletableFuture<ResponseEntity<List<ChatMessage>>> fetchMessageByUserId(@PathVariable String userId) {
        return CompletableFuture.supplyAsync(() -> {
            List<ChatMessage> message = chatService.fetchMessageByUserIdAsync(userId);
            return ResponseEntity.ok(message);
        });
    }
}
