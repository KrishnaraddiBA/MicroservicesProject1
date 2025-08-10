package com.authservice.service;

import com.authservice.dto.APIResponse;
import com.authservice.dto.UserDto;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public APIResponse<String> register(UserDto userDto){
        if (userRepository.existsByUsername(userDto.getUsername())){
            APIResponse<String> response=new APIResponse<>();
            response.setMessage("Registration failed");
            response.setStatus(500);
            response.setData("User with username exists");
            return response;
        }
        if (userRepository.existsByEmail(userDto.getEmail())){
            APIResponse response=new APIResponse();
            response.setMessage("Registration failed");
            response.setStatus(500);
            response.setData("User with user email exists");
            return response;
        }
        User user=new User();
        BeanUtils.copyProperties(userDto,user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        APIResponse<String> response=new APIResponse<>();
        response.setMessage("Registration done");
        response.setStatus(201);
        response.setData("User registration sucesfull");
        return response;
    }



}
