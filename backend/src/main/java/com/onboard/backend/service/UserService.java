package com.onboard.backend.service;

import com.onboard.backend.dto.UserDto;
import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto user);

    public UserDto getUserById(Long id);

    public List<UserDto> getAllUsers();

}