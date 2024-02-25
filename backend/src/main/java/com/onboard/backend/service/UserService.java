package com.onboard.backend.service;

import com.onboard.backend.entity.User;
import com.onboard.backend.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // Validate user data
        // Hash password
        // Perform additional processing
        return userRepository.save(user);
    }

    // Other methods for user management

}