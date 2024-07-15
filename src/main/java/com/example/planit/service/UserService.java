package com.example.planit.service;

import com.example.planit.model.User;
import com.example.planit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Async
    public CompletableFuture<User> saveUser(User user) {
        return CompletableFuture.completedFuture(userRepository.save(user));
    }
}
