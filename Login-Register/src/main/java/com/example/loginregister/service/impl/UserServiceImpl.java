package com.example.loginregister.service.impl;

import com.example.loginregister.dto.request.RequestUpdate;
import com.example.loginregister.dto.response.ResponseData;
import com.example.loginregister.entity.User;
import com.example.loginregister.enums.ErrorCode;
import com.example.loginregister.exception.AppException;
import com.example.loginregister.repo.TokenRepo;
import com.example.loginregister.repo.UserRepo;
import com.example.loginregister.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepo userRepo;
    TokenRepo tokenRepo;
    PasswordEncoder passwordEncoder;
    @Override
    public ResponseData updateUser(RequestUpdate request) {
        return userRepo.findById(request.getId()).map(existUser -> {
            if (userRepo.findByEmail(request.getEmail()).isPresent() &&
                    !existUser.getEmail().equals(request.getEmail())) {
                throw new AppException(ErrorCode.EMAIL_EXIST);
            }

            existUser.setName(request.getName());
            existUser.setBirthday(request.getBirthday());
            existUser.setGender(request.getGender());
            existUser.setAddress(request.getAddress());


            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                existUser.setPassword(passwordEncoder.encode(request.getPassword()));
            } else {
                throw new AppException(ErrorCode.PASSWORD_NOT_EMPTY);
            }

            User updatedUser = userRepo.save(existUser);

            return ResponseData.<User>builder()
                    .status(200)
                    .message("User updated successfully")
                    .result(updatedUser)
                    .build();
        }).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public ResponseData findById(Integer id, String token) {
        String jwtToken = token.substring(7);
        tokenRepo.findByToken(jwtToken).orElseThrow(()-> new AppException(ErrorCode.INVALID_CREDENTIALS));
        userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return ResponseData.builder()
                .status(200)
                .message("Get infor successfully")
                .result(userRepo.findById(id))
                .build();
    }

    @Override
    public ResponseData delete(Integer id, String token) {
        String jwtToken = token.substring(7);
        tokenRepo.findByToken(jwtToken)
                .ifPresentOrElse(tokenRepo::delete,
                        () -> { throw new AppException(ErrorCode.USER_NOT_FOUND); });
        userRepo.findById(id)
                .ifPresentOrElse(userRepo::delete,
                        () -> { throw new AppException(ErrorCode.USER_NOT_FOUND); });
        return ResponseData.builder()
                .status(200)
                .message("Delete successfully")
                .build();
    }

    @Override
    public ResponseData getListCustomer() {
        return ResponseData.builder()
                .code(200)
                .message("Get list customer successfully")
                .result(userRepo.getListCustomer())
                .build();
    }

    @Override
    public ResponseData getListStaff() {
        return ResponseData.builder()
                .code(200)
                .message("Get list customer successfully")
                .result(userRepo.getListStaff())
                .build();
    }

    @Override
    public ResponseData getListManager() {
        return ResponseData.builder()
                .code(200)
                .message("Get list customer successfully")
                .result(userRepo.getListManger())
                .build();
    }

    @Override
    public ResponseData getListAdmin() {
        return ResponseData.builder()
                .code(200)
                .message("Get list customer successfully")
                .result(userRepo.getListAdmin())
                .build();
    }
}
