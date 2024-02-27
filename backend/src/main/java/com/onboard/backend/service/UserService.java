package com.onboard.backend.service;

import com.onboard.backend.entity.User;
import java.util.List;

public interface UserService {
    public String createUser(User user);

    public User getUserDetails(Long id);

    public List<User> getAllUsers();

}