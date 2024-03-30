package com.example.intelligent.chatbot.service;

import com.example.intelligent.chatbot.dto.Chat;
import com.example.intelligent.chatbot.dto.ChatGPTRequest;
import com.example.intelligent.chatbot.dto.ChatGptResponse;
import com.example.intelligent.chatbot.entity.ChatMessage;
import com.example.intelligent.chatbot.utils.ChatStoreUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.example.intelligent.chatbot.constants.ChatConstants.OPENAI_API_URL;
import static com.example.intelligent.chatbot.constants.ChatConstants.OPENAI_MODEL;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    @Value(OPENAI_MODEL)
    private String model;
    @Value(OPENAI_API_URL)
    private String apiURL;
    private final RestTemplate restTemplate;
    private final ChatStoreUtils chatStoreUtils;

    @Autowired
    public ChatService(RestTemplate restTemplate, ChatStoreUtils chatStoreUtils) {
        this.restTemplate = restTemplate;
        this.chatStoreUtils = chatStoreUtils;
    }

    @Async
    public CompletableFuture<String> processMessageAsync(Chat chat) {
        ChatGPTRequest request = new ChatGPTRequest(model, chat.getMessage());
        logger.debug("Sending request to OpenAI API: {}", request);
        ChatGptResponse chatGptResponse = restTemplate.postForObject(apiURL, request, ChatGptResponse.class);
        if (chatGptResponse != null && chatGptResponse.getChoices() != null && !chatGptResponse.getChoices().isEmpty()) {
            String messageResponse = chatGptResponse.getChoices().get(0).getMessage().getContent();
            logger.debug("Received response from OpenAI API: {}", messageResponse);
            chatStoreUtils.saveChatMessage(chat.getUserId(), chat.getMessage(), messageResponse);
            return CompletableFuture.completedFuture(messageResponse);
        } else {
            logger.warn("Empty or null response received from OpenAI API");
        }
        return CompletableFuture.completedFuture("");
    }


    public List<ChatMessage> fetchAllMessageAsync() {
        return chatStoreUtils.chatMessageList();
    }


    public List<ChatMessage> fetchMessageByUserIdAsync(String userId) {
        return chatStoreUtils.chatMessageByUserId(userId);
    }
}
