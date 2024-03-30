package com.example.intelligent.chatbot.utils;

import com.example.intelligent.chatbot.dto.UserDto;
import com.example.intelligent.chatbot.entity.User;

public class UserUtils {

    private UserUtils(){
    }
    public static User createUser(UserDto userDto) {
        return User.builder().firstName(userDto.getFirstName()).lastName(userDto.getLastName()).email(userDto.getEmail()).address(userDto.getAddress()).build();
    }
}
