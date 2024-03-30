package com.example.intelligent.chatbot.service;

import com.example.intelligent.chatbot.dto.UserDto;
import com.example.intelligent.chatbot.entity.User;
import com.example.intelligent.chatbot.repository.UserRepository;
import com.example.intelligent.chatbot.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User saveUser(UserDto userDto) {
        return userRepository.save(UserUtils.createUser(userDto));
    }

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> fetchUserId(String userId) {
        return userRepository.findById(userId);
    }
}
