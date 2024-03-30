package com.example.intelligent.chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Builder
@AllArgsConstructor
@Data
@Document(collection = "users")
public class User {
    @Id
    private String Id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String address;
    public User() {
    }
}
