package com.example.loginregister.service.impl;

import com.example.loginregister.dto.request.RegisterRequest;
import com.example.loginregister.dto.request.UserLoginRequest;
import com.example.loginregister.dto.response.AuthenticationResponse;
import com.example.loginregister.entity.Token;
import com.example.loginregister.entity.TokenType;
import com.example.loginregister.entity.User;
import com.example.loginregister.enums.ErrorCode;
import com.example.loginregister.exception.AppException;
import com.example.loginregister.repo.TokenRepo;
import com.example.loginregister.repo.UserRepo;
import com.example.loginregister.service.AuthenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenServiceImpl implements AuthenService {
    UserRepo userRepository;
    JwtService jwtService;
    TokenRepo tokenRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        savedUserToken(user, jwtToken);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public boolean introspect(String token) {
        Token existedToken = tokenRepository.findByToken(token)
                .orElse(null);

        if (existedToken == null) {
            return false;
        }

        return !jwtService.isTokenExpired(existedToken.getToken());
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .address(request.getAddress())
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        savedUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void savedUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        tokenRepository.deleteAll(validTokens);
    }
}
