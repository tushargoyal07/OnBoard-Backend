package com.onboard.backend.service.impl;

import com.onboard.backend.entity.User;
import com.onboard.backend.exception.UserNotFoundException;
import com.onboard.backend.repository.UserRepository;
import com.onboard.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(User user) {
        userRepository.save(user);
        return "Success";
    }

    @Override
    public User getUserDetails(Long id) {
        if (userRepository.findById(id).isEmpty())
            throw new UserNotFoundException("User with this id does not exist");
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
