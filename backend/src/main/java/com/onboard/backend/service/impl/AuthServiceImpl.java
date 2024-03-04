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

  @Override
  public String signIn(String email, String password) {
    User user = userRepository.findByEmail(email);
    if (passwordEncoder.matches(password, user.getPassword())) {
      return "Logged In Successfully";
    }
    return null;
  }

  // @Override
  // public String signIn(String email, String password) {
  //       // Retrieve user from the database based on email
  //       User user = userRepository.findByEmail(email);

  //       // Check if user exists
  //       if (user == null) {
  //           throw new RuntimeException("User not found");
  //       }

  //       // Check if provided password matches stored password
  //       if (!passwordEncoder.matches(password, user.getPassword())) {
  //           throw new RuntimeException("Invalid password");
  //       }

  //       // Generate JWT token (implement this logic)
  //       // String jwtToken = generateJwtToken(user);
  //       return "Logged In Successfully";
  //       // return jwtToken;
  //   }

    // private String generateJwtToken(User user) {
    //     // Implement JWT token generation logic here
    //     // Example: JWT.create().withSubject(user.getEmail()).sign(Algorithm.HMAC256("secret"));
    //     return "JWT_TOKEN";
    // }


}