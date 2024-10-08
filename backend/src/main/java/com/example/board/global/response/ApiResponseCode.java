package com.example.board.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode {
    OK(200, "E200", "요청이 처리되었습니다"),
    POST_VALIDATION_FAILED(400, "POST_VALIDATION_FAILED", "입력조건이 맞지 않습니다"),
    NOT_MATCHED(401, "NOT_MATCHED", "정보가 일치하지 않습니다"),
    ;

    private int status;
    private final String code;
    private final String message;

    ApiResponseCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
