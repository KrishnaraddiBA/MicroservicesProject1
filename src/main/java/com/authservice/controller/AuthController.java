package com.authservice.controller;

import com.authservice.dto.APIResponse;
import com.authservice.dto.LoginDto;
import com.authservice.dto.UserDto;
import com.authservice.service.AuthService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    //localhost:8085/api/v1/auth/register
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto userDto) {
        APIResponse<String> response = authService.register(userDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    //localhost:8085/api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> loginCheck(@RequestBody LoginDto loginDto) {

        APIResponse<String> response = new APIResponse<>();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        try {
            Authentication authenticate = authManager.authenticate(token);
            if (authenticate.isAuthenticated()) {
                response.setMessage("login sucesfull");
                response.setStatus(201);
                response.setData("login sucesfull u can do anything u want");
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        response.setMessage("Login failed");
        response.setStatus(404);
        response.setData("Login unsucessfull u can try to reenter the username and password");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
}
