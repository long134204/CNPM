package com.example.loginregister.controller;

import com.example.loginregister.dto.request.RegisterRequest;
import com.example.loginregister.dto.request.UserLoginRequest;
import com.example.loginregister.dto.response.ResponseData;
import com.example.loginregister.enums.ErrorCode;
import com.example.loginregister.service.AuthenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {

    AuthenService authenticationService;

    @PostMapping("/register")
    public ResponseData<?> register( @RequestBody RegisterRequest request){
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .message("Register Success!")
                .result(authenticationService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ResponseData<?> login(@RequestBody UserLoginRequest user) {

        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .result(authenticationService.login(user))
                .build();
    }

    @PostMapping("/introspect")
    public ResponseData<?> introspect(@RequestParam String token) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .result(authenticationService.introspect(token))
                .build();
    }
}