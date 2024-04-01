package com.example.intelligent.chatbot.repository;

import com.example.intelligent.chatbot.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {


    public Optional<User> findByUserName(String userName);
}
