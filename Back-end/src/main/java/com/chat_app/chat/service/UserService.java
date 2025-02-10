package com.chat_app.chat.service;

import com.chat_app.chat.entity.Users;
import com.chat_app.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users signUpUser(String username, String name, String email, String password) {
        // Check if the username or email is already taken
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists");
        }

        // Create a new user object
        Users newUser = new Users(username, name, email, password);

        // Save the user object
        return userRepository.save(newUser);
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
