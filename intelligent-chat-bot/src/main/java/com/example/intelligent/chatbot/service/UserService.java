package com.example.intelligent.chatbot.service;

import com.example.intelligent.chatbot.dto.UserDto;
import com.example.intelligent.chatbot.entity.User;
import com.example.intelligent.chatbot.exception.UserAlreadyFoundException;
import com.example.intelligent.chatbot.exception.UserNotFoundException;
import com.example.intelligent.chatbot.repository.UserRepository;
import com.example.intelligent.chatbot.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.intelligent.chatbot.constants.ChatConstants.ERROR_CODE;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserDto userDto) {
        userRepository.findByUserName(userDto.getUserName()).orElseThrow(() -> new UserAlreadyFoundException("UserName already existed in the System", ERROR_CODE));
        return userRepository.save(UserUtils.createUser(userDto));
    }

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> fetchUserId(String userId) {
        return userRepository.findById(userId);
    }

    public void deleteUserId(String userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not found with given User ID", ERROR_CODE));
        userRepository.deleteById(userId);
    }
}
