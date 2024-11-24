package com.example.loginregister.exception;

import com.example.loginregister.dto.response.ResponseData;
import com.example.loginregister.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseData> handlingAppException(AppException exception, WebRequest request) {
        ResponseData error = new ResponseData();
        response(request, error);
        error.setError("Data Invalid");
        error.setCode(exception.getErrorCode().getCode());
        error.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ResponseData> handlingValidException(MethodArgumentNotValidException exception, WebRequest request) {
        ResponseData error = new ResponseData();
        response(request, error);
        String errorName = ErrorCode
                .valueOf(exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
                .getMessage();
        error.setError("Payload Invalid");
        error.setCode(ErrorCode.INVALID_DATA.getCode());
        error.setMessage(errorName);
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.additionalProperty(fieldError.getField(), errorName);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseData> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ResponseData.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ResponseData> handlingRuntimeException(WebRequest request) {
        ResponseData error = new ResponseData();
        response(request, error);
        error.setError("PathVariable Invalid");
        error.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        error.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private void response(WebRequest request, ResponseData error) {
        error.setTimestamp(new Date());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setPath(request.getDescription(false).replace("uri=", ""));
    }
}
