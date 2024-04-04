package com.example.intelligent.chatbot.configuration;


import com.example.intelligent.chatbot.constants.ChatConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import static com.example.intelligent.chatbot.constants.ChatConstants.OPENAI_API_KEY;

@Configuration
public class OpenAIConfiguration {

    @Value(OPENAI_API_KEY)
    private String openaiApiKey;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add(ChatConstants.AUTHORIZATION, "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}