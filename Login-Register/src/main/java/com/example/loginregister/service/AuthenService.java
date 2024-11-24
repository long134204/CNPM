package com.example.loginregister.service;

import com.example.loginregister.dto.request.RegisterRequest;
import com.example.loginregister.dto.request.UserLoginRequest;
import com.example.loginregister.dto.response.AuthenticationResponse;

public interface AuthenService {
    AuthenticationResponse login(UserLoginRequest request);

    boolean introspect(String token);
    AuthenticationResponse register( RegisterRequest request);
}
