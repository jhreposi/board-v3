package com.example.board.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private final boolean result = false;
    private int status;
    private String code;
    private String message;

}
