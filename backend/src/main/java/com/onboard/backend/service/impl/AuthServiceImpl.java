package com.onboard.backend.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.onboard.backend.dto.UserDto;
import com.onboard.backend.entity.User;
import com.onboard.backend.repository.UserRepository;
import com.onboard.backend.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  public AuthServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public String createUser(UserDto userDto) {
    User newUser = this.modelMapper.map(userDto, User.class);
    String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
    newUser.setPassword(encryptedPassword);
    userRepository.save(newUser);
    return "User created successfully";
  }

}