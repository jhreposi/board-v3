package com.example.board.global.exception;

import com.example.board.global.response.ApiResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info("MethodArgumentNotValidException");

        return handleExceptionResult(ApiResponseCode.POST_VALIDATION_FAILED);
    }

    @ExceptionHandler(PasswordNotMatched.class)
    protected ResponseEntity<ErrorResponse> passwordNotMatchedException(PasswordNotMatched e) {
        log.info("passwordNotMatchedException 실행");
        return handleExceptionResult(ApiResponseCode.NOT_MATCHED);
    }

    private ResponseEntity<ErrorResponse> handleExceptionResult(ApiResponseCode responseCode) {

        return ResponseEntity
                .status(responseCode.getStatus())
                .body(makeResponseBody(responseCode));
    }

    private ErrorResponse makeResponseBody(ApiResponseCode responseCode) {
        ErrorResponse response = ErrorResponse.builder()
                .status(responseCode.getStatus())
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .build();

        return response;
    }
}
