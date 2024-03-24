package com.onboard.backend.service;

import com.onboard.backend.dto.UserDto;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public UserDto updateUser(Long id, UserDto user);

    public UserDto getUserById(Long id);

    public List<UserDto> getAllUsers();

    public void deleteUser(Long id);

}
