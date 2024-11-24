package com.example.loginregister.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import javax.management.loading.MLetContent;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CREDENTIALS(1019, "Invalid credentials", HttpStatus.BAD_REQUEST),
    GET_SUCCESSFUL(1010, "Get successful", HttpStatus.OK),
    USER_NOT_FOUND(1011,"User not exist" ,HttpStatus.NOT_FOUND),
    EMAIL_EXIST(1012,"Email is exist", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_EMPTY(1013,"Password not empty", HttpStatus.BAD_REQUEST ),
    INVALID_DATA(1014, "Invalid data", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN);

    Integer code;
    String message;
    HttpStatusCode statusCode;
}
