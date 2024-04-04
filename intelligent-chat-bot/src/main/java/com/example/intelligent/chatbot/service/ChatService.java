package com.example.intelligent.chatbot.service;

import com.example.intelligent.chatbot.dto.ChatDto;
import com.example.intelligent.chatbot.dto.ChatGPTRequest;
import com.example.intelligent.chatbot.dto.ChatGptResponse;
import com.example.intelligent.chatbot.entity.ChatMessage;
import com.example.intelligent.chatbot.exception.UserNotFoundException;
import com.example.intelligent.chatbot.repository.UserRepository;
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

import static com.example.intelligent.chatbot.constants.ChatConstants.*;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    @Value(OPENAI_MODEL)
    private String model;
    @Value(OPENAI_API_URL)
    private String apiURL;
    private final RestTemplate restTemplate;
    private final ChatStoreUtils chatStoreUtils;
    private final UserRepository userRepository;

    @Autowired
    public ChatService(RestTemplate restTemplate, ChatStoreUtils chatStoreUtils, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.chatStoreUtils = chatStoreUtils;
        this.userRepository = userRepository;
    }

    @Async
    public CompletableFuture<String> processMessageAsync(ChatDto chat) {
        boolean userExists = userRepository.existsById(chat.getUserId());
        if (!userExists) {
            throw new UserNotFoundException("User not found with the provided userID", ERROR_CODE);
        }
        ChatGPTRequest request = new ChatGPTRequest(model, chat.getMessage());
        logRequestToOpenAI(request);
        ChatGptResponse chatGptResponse = restTemplate.postForObject(apiURL, request, ChatGptResponse.class);
        if (chatGptResponse != null && !chatGptResponse.getChoices().isEmpty()) {
            String messageResponse = chatGptResponse.getChoices().get(0).getMessage().getContent();
            logResponseFromOpenAI(messageResponse);
            chatStoreUtils.saveChatMessage(chat.getUserId(), chat.getMessage(), messageResponse);
            return CompletableFuture.completedFuture(messageResponse);
        } else {
            logEmptyOrNullResponseFromOpenAI();
            return CompletableFuture.completedFuture("");
        }
    }

    private void logRequestToOpenAI(ChatGPTRequest request) {
        logger.debug("Sending request to OpenAI API: {}", request);
    }

    private void logResponseFromOpenAI(String messageResponse) {
        logger.debug("Received response from OpenAI API: {}", messageResponse);
    }

    private void logEmptyOrNullResponseFromOpenAI() {
        logger.warn("Empty or null response received from OpenAI API");
    }

    public List<ChatMessage> fetchAllMessageAsync() {
        return chatStoreUtils.chatMessageList();
    }

    public List<ChatMessage> fetchMessageByUserIdAsync(String userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with the provided userID", ERROR_CODE));
        return chatStoreUtils.chatMessageByUserId(userId);
    }
}
